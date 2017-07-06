package com.spark.stream

import java.util
import org.apache.kafka.clients.consumer.KafkaConsumer
import scala.collection.JavaConverters._
import java.util.Properties

/**
  * Created by divya on 6/23/17.
  */
object KafkaConsumer {
  def main(args: Array[String]): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("group.id", "retail_iot_topic")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

    val consumer = new KafkaConsumer[String, String](props)
    consumer.subscribe(util.Collections.singletonList("retail_iot_topic"))

    while (true) {
      val records = consumer.poll(100)
      for (record <- records.asScala) {
        println(record)
      }
    }
  }
}