package demo.coral

import org.json4s.jackson.JsonMethods._
import org.scalatest.{Matchers, WordSpec}

import scala.concurrent.duration._

class KafkaProducerSpec extends WordSpec with Matchers {

  "KafkaProducer" should {

    "test" in {
      val builder = Generator.builder()
      builder.addCounter("counter")
      builder.addUniform("a")(0.0, 100.0)
      builder.addGaussian("bb")(100.0, 30.0)
      builder.addLabel("ccc")("xxx", "yyy", "zzz")
      val stream = Generator.stream(builder)
      val strategy = Generator.burstStrategy(100, 1.second, 1000)
      val producer = KafkaProducer("test")
      strategy.excecute(stream, json => {
        val tjson = Generator.timestamp(json)
        println(compact(tjson))
        producer.send(tjson)
      })
    }

    "test2" ignore {
      val producer = KafkaProducer("test")
      producer.send(parse( """{"a": "b"}"""))
    }

  }

}
