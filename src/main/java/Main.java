import com.mongodb.spark.MongoSpark;
import com.mongodb.spark.config.ReadConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

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
        log.info(sb.toString());

        // Create SparkSession with configuration
        log.info("Creating SparkSession...");
        SparkSession sparkSession = SparkSession.builder()
                .master("spark://lattice-100.cs.colostate.edu:8079")
                .appName("experimental_application")
                .config("spark.submit.deployMode", "client")
                .config("spark.executor.cores", "1")
                .config("spark.executor.memory", "4G")
                .config("spark.driver.bindAddress", "0.0.0.0")
                .config("spark.driver.host", System.getenv("POD_IP"))
                .config("spark.driver.port", "50052")
                .getOrCreate();

        // Add dependency jars to SparkContext (if not already exists)
        log.info("Adding dependency JARs to SparkContext...");
        JavaSparkContext sparkContext = new JavaSparkContext(sparkSession.sparkContext());
        for (String jar : sparkJars) {
            if (!sparkContext.jars().contains(jar)) {
                log.info("Dependency JAR {} does not already exist; adding it now", jar);
                sparkContext.addJar(jar);
            } else {
                log.info("Dependency JAR {} already exists", jar);
            }
        }

        // Create MongoDB ReadConfig
        Map<String, String> readOverrides = new HashMap<>();
        readOverrides.put("uri", "mongodb://lattice-100:27018");
        readOverrides.put("database", "sustaindb");
        readOverrides.put("collection", "mpb_cypress_hill_sk_100m");
        ReadConfig readConfig = ReadConfig.create(sparkContext.getConf(), readOverrides);

        // Load Dataset
        Dataset<Row> mongoCollectionDs = MongoSpark.load(sparkContext, readConfig).toDS(Row.class);
        mongoCollectionDs.show(10);

    }
}
