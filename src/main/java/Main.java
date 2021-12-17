import com.mongodb.spark.MongoSpark;
import com.mongodb.spark.config.ReadConfig;
import com.mongodb.spark.rdd.api.java.JavaMongoRDD;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.bson.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                .config("spark.dynamicAllocation.enabled", "false")
                .config("spark.executor.instances", "1")
                .config("spark.executor.cores", "1")
                .config("spark.executor.memory", "4g")
                .config("spark.driver.bindAddress", "0.0.0.0")
                .config("spark.driver.host", System.getenv("POD_IP"))
                .config("spark.driver.port", "50052")
                .config("spark.driver.blockManager.port", "50053")
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
        readOverrides.put("collection", "neon_2d_wind");
        readOverrides.put("readConcern.level", "available");
        //readOverrides.put("partitioner", "MongoShardedPartitioner");
        //readOverrides.put("partitioner.shardKey", "site");
        ReadConfig readConfig = ReadConfig.create(sparkContext.getConf(), readOverrides);

        // Load Dataset
        JavaMongoRDD<Document> mongoCollectionRDD = MongoSpark.load(sparkContext, readConfig);
        Dataset<Row> mongoCollectionDS = mongoCollectionRDD.toDF();
        mongoCollectionDS.printSchema();
        System.err.printf(">>> RECORD COUNT: %d\n", mongoCollectionDS.count());

        mongoCollectionDS.show(10);

        sparkContext.close();
        sparkSession.close();
    }
}
