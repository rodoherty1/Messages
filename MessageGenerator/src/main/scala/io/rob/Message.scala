package io.rob

import io.rob.MessageType.MessageType

/**
 * Created on 02/04/15.
 */
object MessageType extends Enumeration {
  type MessageType = Value

  val REQUESTED = Value("Requested")
  val ACCEPTED = Value("Accepted")
  val REJECTED = Value("Rejected")
  val CANCELLED = Value("Cancelled")
}

case class Message(messageType: MessageType, msg: String)
