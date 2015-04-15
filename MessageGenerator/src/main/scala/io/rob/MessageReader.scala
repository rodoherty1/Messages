package io.rob

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorLogging, ActorSystem, Props}

/**
 * Created on 15/04/15.
 */

class MessageReader extends Actor with ActorLogging {

  implicit val ec = context.dispatcher

  override def receive: Receive = ???


}
