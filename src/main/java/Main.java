import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

import java.util.Arrays;
import java.util.List;

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

    public static final String SPARK_MASTER     = "spark://lattice-100:8079";
    public static final String APP_NAME         = "experimental_application";
    public static final String EXECUTOR_CORES   = "1";
    public static final String EXECUTOR_MEMORY  = "4G";

    public static void main(String[] programArgs) {

        // Print args
        StringBuilder sb = new StringBuilder("Program Arguments:\n");
        for (int i = 0; i < programArgs.length; i++) {
            sb.append(String.format("[%d] %s\n", i, programArgs[i]));
        }
        log.info(sb.toString());

        // Create SparkSession
        log.info("Creating SparkSession...");
        SparkSession sparkSession = SparkSession.builder()
                .master(SPARK_MASTER)
                .appName(APP_NAME)
                .config("spark.executor.cores", EXECUTOR_CORES)
                .config("spark.executor.memory", EXECUTOR_MEMORY)
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


    }
}
