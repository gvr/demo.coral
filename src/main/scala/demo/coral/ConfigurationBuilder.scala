package demo.coral

import java.util.Properties

import com.typesafe.config.{Config, ConfigFactory}

import scala.collection.JavaConverters._

case class ConfigurationBuilder(path: String) {

  private lazy val config: Config = ConfigFactory.load.getConfig(path)

  def properties: Properties = {
    val props = new Properties()
    val it = config.entrySet().asScala
    it.foreach { entry => props.setProperty(entry.getKey, entry.getValue.unwrapped.toString) }
    props
  }

}
