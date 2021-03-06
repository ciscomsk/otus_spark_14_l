import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

trait SparkSessionWrapper extends Serializable {
  lazy val spark: SparkSession = SparkSession
    .builder
    .appName("ProdML")
    .getOrCreate

  Logger.getLogger("org").setLevel(Level.OFF)
}

