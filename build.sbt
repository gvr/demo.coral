name := "demo.coral"

version := "1.0"

scalaVersion := "2.11.6"

crossPaths := false

scalacOptions ++= Seq("-feature", "-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= Seq(
  "org.json4s" %% "json4s-jackson" % "3.2.11",
  "org.json4s" %% "json4s-native" % "3.2.11",
  "org.apache.kafka" %% "kafka" % "0.8.2.1",
  "org.apache.commons" % "commons-math3" % "3.5",
  "com.typesafe" % "config" % "1.2.1",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)