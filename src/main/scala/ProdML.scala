import org.apache.spark.sql.{DataFrame, SaveMode}
import org.apache.spark.ml.PipelineModel

object ProdML extends App with SparkSessionWrapper {
  if (args.length != 3) {
    println("Usage: ProdML <path-to-model> <path-to-input> <path-to-output>")
    sys.exit(-1)
  }

  import spark.implicits._

  try {
    val model: PipelineModel = PipelineModel.load(args(0))

    val data: DataFrame = spark
      .read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv(args(1))

    val prediction: DataFrame = model.transform(data)

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
