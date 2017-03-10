package com.bmesta.engine

import akka.actor.{ActorSystem, Props}
import com.bmesta.engine.MyActor.{Goodbye, Greeting}

/**
  * @author Baptiste Mesta.
  */
object Engine extends App {

  val system = ActorSystem("Hello")
  val a = system.actorOf(Props[MyActor], "helloWorld")
  system.actorOf(Props(classOf[Terminator], a), "terminator")
  a ! Greeting("john")
  a ! Goodbye
}

