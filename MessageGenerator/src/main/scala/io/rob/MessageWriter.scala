package io.rob

import akka.actor.{Actor, ActorLogging}

/**
  * Created on 15/04/15.
  */
class MessageWriter extends Actor with ActorLogging {

   implicit val ec = context.dispatcher

   override def receive: Receive = {
     case Message(messageType, message) => println(s"Received $messageType: $message")
     case _ => println("Message not supported!")
   }


 }
