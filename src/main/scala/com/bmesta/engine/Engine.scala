package com.bmesta.engine

import akka.actor.{ActorSystem, Props}

/**
  * @author Baptiste Mesta.
  */
object Engine extends App {

  val system = ActorSystem("Hello")
  val a = system.actorOf(Props[HelloWorld], "helloWorld")
  system.actorOf(Props(classOf[Terminator], a), "terminator")
}

