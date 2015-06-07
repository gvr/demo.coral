package demo.coral

import java.util.Properties

import kafka.producer.{KeyedMessage, Producer, ProducerConfig}
import org.json4s.JsonAST.JValue
import org.json4s.jackson.JsonMethods._

object KafkaProducer {

  val properties = ConfigurationBuilder("kafka.producer").properties


  def apply(topic: String) = new KafkaProducer(topic, properties)

}

class KafkaProducer(topic: String, properties: Properties) {

  def encode(message: JValue): Array[Byte] = {
    compact(message).getBytes("UTF-8")
  }

  val producer = new Producer[String, Array[Byte]](new ProducerConfig(properties))

  def send(message: JValue): Unit = {
    val keyedMessage = new KeyedMessage[String, Array[Byte]](topic, encode(message))
    producer.send(keyedMessage)
  }

}
