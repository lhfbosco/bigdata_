import java.util.{Properties, UUID}

import scala.util.parsing.json._
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.{ProcessAllWindowFunction, ProcessWindowFunction}
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

object Top5_cities {
  /**
   * 输入的主题名称
   */
  val inputTopic = "mn_buy_ticket_demo2"
  /**
   * kafka地址
   */
  val bootstrapServers = "bigdata35.depts.bingosoft.net:29035,bigdata36.depts.bingosoft.net:29036,bigdata37.depts.bingosoft.net:29037"

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val kafkaProperties = new Properties()
    kafkaProperties.put("bootstrap.servers", bootstrapServers)
    kafkaProperties.put("group.id", UUID.randomUUID().toString)
    kafkaProperties.put("auto.offset.reset", "earliest")
    kafkaProperties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    kafkaProperties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    val kafkaConsumer = new FlinkKafkaConsumer010[String](inputTopic,
      new SimpleStringSchema, kafkaProperties)
    kafkaConsumer.setCommitOffsetsOnCheckpoints(true)
    val inputKafkaStream = env.addSource(kafkaConsumer)

    inputKafkaStream.map {
      x =>
        val j = JSON.parseFull(x) match {//解析kafka接受的数据
          case Some(map: Map[String, Any]) => map
        }
        (j("destination").toString, 1)//取出destination键对应的值，并转为二元组
    }.keyBy(_._1).sum(1)//按目的地聚合
      .keyBy(_._1)//因为聚合后没有key，因此需要再keyBy一次
      .timeWindow(Time.seconds(10))//设置30s的窗口
      .process(new ProcessWindowFunction[(String, Int), (String, Int), String, TimeWindow] {
        override def process(key: String, context: Context, elements: Iterable[(String, Int)], out: Collector[(String, Int)]): Unit = {
          out.collect(elements.maxBy(_._2))//ProcessWindowFunction是增量计算的，因此通过maxBy提取最大的次数
        }
      })
      .timeWindowAll(Time.seconds(10))
      .process(new ProcessAllWindowFunction[(String, Int), (String, Int), TimeWindow] {
        override def process(context: Context, elements: Iterable[(String, Int)], out: Collector[(String, Int)]): Unit = {
          val list = elements.toList
          val sorted = list.sortBy(_._2)//按次数排序，默认升序
          val top5 = sorted.reverse.take(5)//取出前五的城市
          top5.foreach(println)
        }
      })

//    inputKafkaStream.map(x => println(x))
    env.execute()
  }
}
