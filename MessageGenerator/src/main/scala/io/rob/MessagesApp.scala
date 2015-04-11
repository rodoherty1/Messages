package io.rob

import java.util.concurrent._

import scala.concurrent.Future
import scala.util.{Failure, Success}

import akka.actor.ActorSystem
import scala.language.postfixOps
import scala.concurrent.duration._

/**
 * Created on 02/04/15.
 */
object MessagesApp extends App {
  val queue = new LinkedBlockingQueue[String]()

  val actorSystem = ActorSystem()
  val scheduler = actorSystem.scheduler
  implicit val executor = actorSystem.dispatcher

  def createMessage = queue.offer("Hello")

  def readMessage(): Unit = {
    val f = Future[String] {
      queue.poll(10l, TimeUnit.SECONDS)
    }

    f.onComplete({
      case Success(s) => println(s)
      case Failure(e) => println(s"Failed with error $e")
    })
  }

  def installShutdownHook(): Unit = {
    Runtime.getRuntime.addShutdownHook(new Thread() {
      override def run(): Unit = {
        try {
          actorSystem.shutdown()
          println("Shutting down")
          actorSystem.awaitTermination()
          println("App has shut down")
        } catch {
          case e: Exception => println("Error shutting down: " + e.getMessage)
        }
      }
    })
  }

  // do regularly every period millis starting now
  def doRegularly(fn: => Unit, period:Long): Unit = {
    scheduler.schedule(0 seconds, period seconds)(fn)
  }

  installShutdownHook()
  doRegularly(createMessage, 5l)
  doRegularly(readMessage, 5l)
}