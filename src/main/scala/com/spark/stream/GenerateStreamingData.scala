package com.spark.stream

import java.io.{File, PrintWriter}
import scala.io.Source

/**
  * Created by divya on 6/22/17.
  */
object GenerateStreamingData {
  def main(Args: Array[String]) {

    val pw = new PrintWriter(new File("/Users/divya/Downloads/output.csv"))
    // var inputFile = io.Source.fromFile(" ")
    //val inputFile = io.Source.fromFile("/Users/divya/Downloads/retail_iot_elapsedtime")

    var count = 1
    while (count < 10) {
      val inputFile = Source.fromFile("/Users/divya/Downloads/retail_iot_elapsedtime")
      for (line <- inputFile.getLines) {
        //val cols = line.split(",").map(_.trim)
        //println(s"count: $count cols: ${cols(0)},${cols(1)},${cols(2)},${cols(3)},${cols(4)},${cols(5)},${cols(6)},${cols(7)}")
        pw.write(line)
      }
      count += 1
      inputFile.close()
    }
    pw.close
  }
}
