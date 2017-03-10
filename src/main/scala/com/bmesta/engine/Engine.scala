package com.bmesta.engine

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props, Terminated}

/**
  * @author Baptiste Mesta.
  */
object Engine extends App {

  val system = ActorSystem("Hello")
  val a = system.actorOf(Props[HelloWorld], "helloWorld")
  system.actorOf(Props(classOf[Terminator], a), "terminator")
}

class Terminator(ref: ActorRef) extends Actor with ActorLogging {
  context watch ref
  def receive = {
    case Terminated(_) =>
      log.info("{} has terminated, shutting down system", ref.path)
      context.system.terminate()
  }
}