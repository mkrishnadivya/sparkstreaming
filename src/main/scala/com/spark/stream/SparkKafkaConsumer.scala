package com.spark.stream

import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.sql._
import org.apache.spark.sql.types.{StringType, StructField, StructType}
//import com.amazonaws.services.s3.AmazonS3Client
//import com.amazonaws.auth.BasicAWSCredentials


/**
  * Created by divya on 6/26/17.
  */


object SparkKafkaConsumer {
  def main(arg: Array[String]) {

    val conf = new SparkConf().setMaster("local[*]").setAppName("SparkKafkaConsumer")
    val ssc = new StreamingContext(conf, Seconds(10))

    val AWS_ACCESS_KEY = "****"
    val AWS_SECRET_KEY = "****"

    //val yourAWSCredentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY)

    val topic = "retail_iot_topic"
    val zkhosts = "localhost"
    val zkports = "2181"

    val numberOfReceivers = 1

    val kafkaProperities: Map[String, String] =
      Map("zookeeper.hosts" -> zkhosts,
        "zookeeper.ports" -> zkports,
        "kafka.topic" -> topic,
        "zookeeper.consumer.connection" -> "localhost:2181",
        "kafka.consumer.id" -> "kafka-consumer"
      )
    val props = new java.util.Properties()

    kafkaProperities foreach { case (key, value) => props.put(key, value) }

    val kafkaStream = KafkaUtils.createStream(ssc, "localhost:2181", "retail_iot_consumer_topic", Map("retail_iot_topic" -> 5))

    val transform1Dstream = kafkaStream.map(cols => cols._2.split(",")).map { cols => org.apache.spark.sql.Row(cols(0), cols(1), cols(2), cols(3), cols(4), cols(5), cols(6), cols(7)) } //for other way of conversion to DF

    val transform2Dstream = transform1Dstream.foreachRDD { rdd =>
      // Get the singleton instance of SparkSession
      val spark = SparkSession.builder.config(conf).getOrCreate()
      import spark.implicits._

      case class Purchases(TagID: String, Source_Location: String, Stage2: String, Stage3: String, Destination_Loaction: String, Time_Ordered: String, Time_Delivered: String, Elapsed_Time: String)

      // other way of converting rdd to dataframe but rdd should be of type rdd[Row]
      val schema = StructType(Seq(
        StructField("TagID", StringType, nullable = false),
        StructField("Source_Location", StringType, nullable = false),
        StructField("Stage2", StringType, nullable = false),
        StructField("Stage3", StringType, nullable = false),
        StructField("Destination_Loaction", StringType, nullable = false),
        StructField("Time_Ordered", StringType, nullable = false),
        StructField("Time_Delivered", StringType, nullable = false),
        StructField("Elapsed_Time", StringType, nullable = false)
      ))

      // Convert RDD[Row] to DataFrame
      val output = spark.createDataFrame(rdd, schema)
        .coalesce(20)
        .write
        .format("csv")
        .option("accessKey", AWS_ACCESS_KEY)
        .option("secretKey", AWS_SECRET_KEY)
        .option("bucket", "divya-spark-dataset")
        .save("/streaming/")
        //.csv("/streaming-output.csv")


      /*  val output1 = rdd.map { cols => Purchases(cols(0), cols(1), cols(2), cols(3), cols(4), cols(5), cols(6), cols(7)) }.toDF()
        .coalesce(20)
        .write
        .format("csv")
        .csv("s3n://divya-spark-dataset/streaming-output.csv")
        */
    }

    ssc.start()
    ssc.awaitTermination()
  }
}
