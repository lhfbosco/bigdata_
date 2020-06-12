import java.util.{Properties, UUID}

import Main.{bootstrapServers, topic}
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010
import org.apache.flink.streaming.util.serialization.JSONKeyValueDeserializationSchema
import org.apache.flink.util.Collector

object Consumer {

  val accessKey = "9DCE9324A61490675D3D"
  val secretKey = "WzBGNEFBQTI2REIyOTc4N0RERkFBMjQ2NTIyQUM4OTFDMjRCMzIyNjBd"
  //s3地址
  val endpoint = "scuts3.depts.bingosoft.net:29999"
  //上传到的桶
  val bucket = "mafeifei"
  //上传文件的路径前缀
  val keyPrefix = "consumer/"
  //上传数据间隔 单位毫秒
  val period = 5000
  //输入的kafka主题名称
  val inputTopic = "lhf"
  //kafka地址
  val bootstrapServers = "bigdata35.depts.bingosoft.net:29035,bigdata36.depts.bingosoft.net:29036,bigdata37.depts.bingosoft.net:29037"



  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    val kafkaProperties = new Properties()
    kafkaProperties.put("bootstrap.servers", bootstrapServers)
    kafkaProperties.put("group.id", UUID.randomUUID().toString)
    kafkaProperties.put("auto.offset.reset", "earliest")
    kafkaProperties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    kafkaProperties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    val kafkaConsumer = new FlinkKafkaConsumer010[ObjectNode](topic,
      new JSONKeyValueDeserializationSchema(false), kafkaProperties)
    kafkaConsumer.setCommitOffsetsOnCheckpoints(true)

    val inputKafkaStream = env.addSource(kafkaConsumer)

    inputKafkaStream.map(x=>(x.get("value").get("destination").asText, x.get("value").toString))
      .keyBy(_._1)
      .timeWindow(Time.seconds(5))
      .process(new ProcessWindowFunction[(String, String), String, String, TimeWindow] {
        override def process(key: String, context: Context, elements: Iterable[(String, String)], out: Collector[String]): Unit = {
          var result = ""
          for(e <- elements){
            result += e._2 + "\n"
          }
          out.collect(result)
        }
      })
      .writeUsingOutputFormat(new S3Writer(accessKey,secretKey, endpoint, bucket, keyPrefix, period))
    env.execute()
  }
}
