package com.bmesta.engine

import akka.actor.{Actor, ActorLogging, ActorRef, Terminated}

/**
  * @author Baptiste Mesta.
  */
class Terminator(ref: ActorRef) extends Actor with ActorLogging {
  context watch ref
  def receive = {
    case Terminated(_) =>
      log.info("{} has terminated, shutting down system", ref.path)
      context.system.terminate()
  }
}
