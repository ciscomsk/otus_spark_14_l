import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

trait SparkSessionWrapperLocal extends Serializable {
  lazy val spark: SparkSession = SparkSession
    .builder
    .master("local[*]")
    .appName("ProdML")
    .getOrCreate

  Logger.getLogger("org").setLevel(Level.OFF)
}

