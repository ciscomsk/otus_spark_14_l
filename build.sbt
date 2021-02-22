name := "14"

version := "0.1"

scalaVersion := "2.12.13"

val sparkVersion = "3.0.2"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-mllib" % sparkVersion % "provided"
)