package com.bmesta.engine

import akka.actor.{ActorSystem, Props}

/**
  * @author Baptiste Mesta.
  */
object Engine extends App {

  val system = ActorSystem("Hello")
  val a = system.actorOf(Props[ExamplePersistentActor], "helloWorld")
  system.actorOf(Props(classOf[Terminator], a), "terminator")
  println("before")
  a ! "print"
  println("cmd john")
  a ! Cmd("john")
  a ! "print"
  println("cmd john")
  a ! Cmd("jack")
  a ! "print"
}

