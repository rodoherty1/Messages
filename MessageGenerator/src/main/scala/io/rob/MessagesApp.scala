package io.rob


import java.util.concurrent._

import com.sun.deploy.net.HttpResponse

import scala.concurrent.Future
import scala.util.{Failure, Success}

import akka.actor.ActorSystem
import scala.language.postfixOps
import scala.concurrent.duration._
import scala.concurrent.Future

import akka.actor.ActorSystem
import akka.util.Timeout
import akka.pattern.ask
import akka.io.IO

import spray.can.Http
import spray.http._
import HttpMethods._

// or, with making use of spray-httpx
import spray.httpx.RequestBuilding._



/**
 * Created on 02/04/15.
 */
object MessagesApp extends App {
  val queue = new LinkedBlockingQueue[String]()

  implicit val actorSystem: ActorSystem = ActorSystem()
  val scheduler = actorSystem.scheduler
  implicit val executor = actorSystem.dispatcher

  implicit val timeout: Timeout = Timeout(15.seconds)
  import actorSystem.dispatcher // implicit execution context

  def createMessage = queue.offer("Hello")

  def readMessage(): Unit = {
    val f = Future[String] {
      queue.poll(10l, TimeUnit.SECONDS)
    }

    val response: Future[HttpResponse] = (IO(Http) ? Get("http://spray.io")).mapTo[HttpResponse]

    val statusCode: Future[Int] = f.flatMap(_ => response.map(response => response.getStatusCode))
    statusCode.onComplete(code => println (s"Return Code: $code"))

//    f.onComplete({
//      case Success(s) => println (s)
//      case Failure(e) => println(s"Failed with error $e")
//    })
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

//  val systemActorSystem: ActorSystem = ActorSystem("system")

  installShutdownHook()
  doRegularly(createMessage, 5l)
  doRegularly(readMessage(), 5l)
}
