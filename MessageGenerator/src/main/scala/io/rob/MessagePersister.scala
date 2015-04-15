package io.rob

import akka.actor.{Actor, ActorLogging}

/**
 * Created on 15/04/15.
 */
class MessagePersister extends Actor with ActorLogging {

  implicit val ec = context.dispatcher


  override def receive: Receive = {
    case Message(messageType, message) => writeToFile(message)
    case _ => println("Message not supported!")
  }

  def writeToFile(message: String): Unit = {

  }

}
