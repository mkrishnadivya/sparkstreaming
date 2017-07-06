name := "SparkStreaming"

version := "1.0"

scalaVersion := "2.11.8"

/*libraryDependencies += "org.apache.spark" % "spark-core_2.10" % "2.0.1"
libraryDependencies += "org.apache.spark" % "spark-streaming_2.10" % "2.0.0"
libraryDependencies += "org.apache.spark" % "spark-streaming-kafka_2.10" % "1.6.2"
libraryDependencies += "org.apache.kafka" % "kafka_2.10" % "0.10.1.1"
*/


libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.0.1"
libraryDependencies += "org.apache.spark" % "spark-streaming_2.11" % "2.0.1"
libraryDependencies += "org.apache.kafka" % "kafka_2.11" % "0.10.1.1"
libraryDependencies += "org.apache.spark" % "spark-streaming-kafka-0-8_2.11" % "2.0.2"
libraryDependencies += "org.apache.spark" % "spark-sql_2.11" % "2.0.2"
libraryDependencies += "com.amazonaws" % "aws-java-sdk-s3" % "1.11.155"


