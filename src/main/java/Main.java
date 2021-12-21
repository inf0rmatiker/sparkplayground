import com.mongodb.spark.MongoSpark;
import com.mongodb.spark.config.ReadConfig;
import com.mongodb.spark.rdd.api.java.JavaMongoRDD;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.ml.linalg.Vectors;
import org.apache.spark.ml.regression.LinearRegressionModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.bson.Document;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;

public class Main {

    private static final Logger log = LogManager.getLogger(Main.class);

    // These jar dependencies must be present on every executor in the Spark cluster
    public static final List<String> sparkJars = List.of(
            "build/libs/mongo-spark-connector_2.12-3.0.1.jar",
            "build/libs/spark-core_2.12-3.0.1.jar",
            "build/libs/spark-mllib_2.12-3.0.1.jar",
            "build/libs/spark-sql_2.12-3.0.1.jar",
            "build/libs/bson-4.0.5.jar",
            "build/libs/mongo-java-driver-3.12.5.jar"
    );

    public static void main(String[] programArgs) {

        // Print args
        StringBuilder sb = new StringBuilder("Program Arguments:\n");
        for (int i = 0; i < programArgs.length; i++) {
            sb.append(String.format("[%d] %s\n", i, programArgs[i]));
        }
        System.err.println(sb);

        // Create SparkSession with configuration
        System.err.println("Creating SparkSession...");
        SparkSession sparkSession = SparkSession.builder()
                .master("spark://lattice-100.cs.colostate.edu:8079")
                .appName("experimental_application")
                .config("spark.submit.deployMode", "client")
                .config("spark.executor.cores", "5")
                .config("spark.executor.memory", "8G")
                .config("spark.dynamicAllocation.enabled", "true")
                .config("spark.dynamicAllocation.initialExecutors", "5")
                .config("spark.dynamicAllocation.shuffleTracking.enabled", "true")
                .config("spark.dynamicAllocation.minExecutors", "1")
                .config("spark.dynamicAllocation.maxExecutors", "10")
                .config("spark.dynamicAllocation.schedulerBacklogTimeout", "10s")
                .config("spark.dynamicAllocation.executorIdleTimeout", "10s")
                .config("spark.driver.bindAddress", "0.0.0.0")
                .config("spark.driver.host", System.getenv("POD_IP"))
                .config("spark.driver.port", "50052")
                .config("spark.driver.blockManager.port", "50053")
                .config("mongodb.keep_alive_ms", "100000")
                .getOrCreate();

        // Add dependency jars to SparkContext (if not already exists)
        System.err.println("Adding dependency JARs to SparkContext...");
        JavaSparkContext sparkContext = new JavaSparkContext(sparkSession.sparkContext());
        for (String jar : sparkJars) {
            if (!sparkContext.jars().contains(jar)) {
                System.err.printf("Dependency JAR %s does not already exist; adding it now\n", jar);
                sparkContext.addJar(jar);
            } else {
                System.err.printf("Dependency JAR %s already exists\n", jar);
            }
        }

        // Create MongoDB ReadConfig
        Map<String, String> readOverrides = new HashMap<>();
        readOverrides.put("uri", "mongodb://lattice-100.cs.colostate.edu:27018/");
        readOverrides.put("database", "sustaindb");
        readOverrides.put("collection", "noaa_nam");
        readOverrides.put("readConcern.level", "available");
        // readOverrides.put("partitioner", "MongoShardedPartitioner");
        // readOverrides.put("partitioner.shardKey", "site");
        ReadConfig readConfig = ReadConfig.create(sparkContext.getConf(), readOverrides);

        // Load Dataset (lazily)
        JavaMongoRDD<Document> mongoCollectionRdd = MongoSpark.load(sparkContext, readConfig);

        // Specify MongoDB pipeline for loading data
        JavaMongoRDD<Document> aggregatedRdd = mongoCollectionRdd.withPipeline(
                Arrays.asList(
                        Document.parse("{ $match:   { GISJOIN: G4802470 } }"),
                        Document.parse("{ $project: { " +
                                "_id: 0, " +
                                "RELATIVE_HUMIDITY_2_METERS_ABOVE_SURFACE_PERCENT: 1, " + // label
                                "TEMPERATURE_TROPOPAUSE_KELVIN: 1 " +                     // feature
                                "} }")
                )
        );

        // Pull collection from MongoDB into Spark and print inferred schema and count
        Dataset<Row> mongoCollectionDs = aggregatedRdd.toDF();
        mongoCollectionDs.printSchema();
        System.err.printf(">>> RECORD COUNT: %d\n", aggregatedRdd.count());
        mongoCollectionDs.show(10);

        Dataset<Row> labeledDs = mongoCollectionDs.withColumnRenamed(
                "RELATIVE_HUMIDITY_2_METERS_ABOVE_SURFACE_PERCENT", "label"
        );

        // Create a Vector column for features
        VectorAssembler vectorAssembler = new VectorAssembler()
                .setInputCols(new String[]{"TEMPERATURE_TROPOPAUSE_KELVIN"})
                .setOutputCol("features");
        Dataset<Row> vectorizedDs = vectorAssembler.transform(labeledDs).select("features", "label");

        // Create an "already trained" Linear Regression Model
        Vector coefficients = Vectors.dense(1.0);
        double intercept = 3.0;
        LinearRegressionModel lrModel = new LinearRegressionModel("Test Model", coefficients, intercept);

        lrModel.transform(vectorizedDs).select("features", "label", "prediction").show(10);

        sparkContext.close();
        sparkSession.close();
    }
}
