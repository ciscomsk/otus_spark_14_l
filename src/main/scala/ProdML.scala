import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.ml.PipelineModel

object ProdML {
  def main(args: Array[String]): Unit = {
    if (args.length != 3) {
      println("Usage: ProdML <path-to-model> <path-to-input> <path-to-output>")
      sys.exit(-1)
    }

    val spark = SparkSession.builder
      .appName("ProdML")
      .getOrCreate()

    import spark.implicits._

    try {
      val model = PipelineModel.load(args(0))

      val data = spark.read
        .option("header", "true")
        .option("inferSchema", "true")
        .csv(args(1))

      val prediction = model.transform(data)

      prediction
        .filter($"prediction" === 1)
        .select("CLIENTNUM")
        .repartition(1)
        .write
        .mode(SaveMode.Overwrite)
        .csv(args(2))

    } catch {
      case e: Exception =>
        println(s"ERROR: ${e.getMessage}")
        sys.exit(-1)
    }
  }
}