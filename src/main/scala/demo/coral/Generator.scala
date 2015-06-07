package demo.coral

import org.json4s.JsonAST.JValue
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods.render

import scala.concurrent.duration.Duration

object Generator {

  def builder() = new GeneratorBuilder(Random)

  def stream(builder: GeneratorBuilder): Stream[JValue] = {
    val generator = builder.map
    map(generator)
  }

  def map(streams: Map[String, Stream[JValue]]): Stream[JValue] = {
    var next = streams
    Stream.continually {
      val head = next.map { case (k, v) => (k, v.head) }
      next = next.map { case (k, v) => (k, v.tail) }
      render(head)
    }
  }

  def timestamp(json: JValue): JValue = render("timestamp" -> render(System.currentTimeMillis())).merge(json)

  def burstStrategy(burst: Int, dt: Duration, max: Int) = new GeneratorBurstStrategy[JValue](burst, dt, max)



}

class GeneratorBuilder(random: Random) {

  var map: Map[String, Stream[JValue]] = Map.empty

  def add(name: String, stream: Stream[JValue]): Unit = map += (name -> stream)

  def addCounter(name: String): Unit = add(name, Stream.from(1).map(render(_)))

  def addUniform(name: String)(min: Double, max: Double): Unit = add(name, random.uniform(min, max).map(render(_)))

  def addGaussian(name: String)(mean: Double, sd: Double): Unit = add(name, random.gaussian(mean, sd).map(render(_)))

  def addLabel(name: String)(labels: String*): Unit = add(name, random.label(labels: _*).map(render(_)))

}

trait GeneratorStrategy[T] {

  def excecute(stream: Stream[T], runElement: T => Unit): Unit

}

class GeneratorBurstStrategy[T](burst: Int, dt: Duration, max: Int) extends GeneratorStrategy[T] {

  def excecute(stream: Stream[T], executeElement: T => Unit): Unit = {
    var n = 0
    stream.take(max).foreach { e =>
      n += 1
      if (n > burst) {
        Thread.sleep(dt.toMillis)
        n = 1
      }
      executeElement(e)
    }
  }

}