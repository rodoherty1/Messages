name := "Messages Generator"

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.12.2" % "test"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.2"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.4-SNAPSHOT"

libraryDependencies += "io.spray" %% "spray-can" % "1.3.1"
libraryDependencies += "io.spray" %% "spray-http" % "1.3.1"
libraryDependencies += "io.spray" %% "spray-httpx" % "1.3.1"


resolvers += "spray repo" at "http://repo.spray.io"

resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"

mainClass in (Compile, run) := Some("io.rob.MessagesApp")
