package demo.coral

import org.apache.commons.math3.random.RandomDataGenerator

object Random extends Random(new RandomDataGenerator()) {

  def apply(source: RandomDataGenerator): Random = new Random(source)

}

class Random(source: RandomDataGenerator) {

  def uniform(): Stream[Double] = uniform(0.0, 1.0)

  def uniform(min: Double, max: Double): Stream[Double] = {
    Stream.continually(source.nextUniform(min, max))
  }

  def gaussian(mean: Double, sd: Double): Stream[Double] = {
    Stream.continually(source.nextGaussian(mean, sd))
  }

  def label(s: String*): Stream[String] = {
    val a = s.toArray
    Stream.continually(a(source.nextInt(0, a.length - 1)))
  }

  def binomial(p: Double): Stream[Boolean] = {
    val uniformStream = uniform(0.0, 1.0)
    uniformStream.map(x => x < p)
  }

}
