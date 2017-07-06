package com.spark.stream

import java.util.Properties
import org.apache.kafka.clients.producer._
import scala.io.Source


/**
  * Created by divya on 6/23/17.
  */
object KafkaProducer {
  def  main(args: Array[String]) {

    val  props = new Properties()

    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)

    var count = 1
    while (count < 100) {
      val inputFile = Source.fromFile("/Users/divya/Downloads/retail_iot_elapsedtime")
      for (line <- inputFile.getLines) {
        val cols = line.split(",").map(_.trim)
        val record = new ProducerRecord("retail_iot_topic", cols(0),line)
        producer.send(record)
      }
      count += 1
      inputFile.close()
    }
  }
}
