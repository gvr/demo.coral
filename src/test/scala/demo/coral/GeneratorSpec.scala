package demo.coral

import org.json4s.jackson.JsonMethods._
import org.scalatest.{Matchers, WordSpec}

import scala.concurrent.duration._

class GeneratorSpec extends WordSpec with Matchers {

  "Generator" should {

    "test" in {
      val builder = Generator.builder()
      builder.addCounter("counter")
      builder.addUniform("a")(0.0, 100.0)
      builder.addGaussian("bb")(100.0, 30.0)
      builder.addLabel("ccc")("xxx", "yyy", "zzz")
      val stream = Generator.stream(builder)
      val strategy = Generator.burstStrategy(3, 1.second, 10)
      strategy.excecute(stream, json => {
        println(compact(Generator.timestamp(json)))
      })
    }

  }

}
