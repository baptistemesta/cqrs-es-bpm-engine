package com.bmesta.engine

import akka.actor.{Actor, ActorLogging}

object MyActor {

  case class Greeting(from: String)

  case object Goodbye

}

class MyActor extends Actor with ActorLogging {

  import MyActor._

  def receive = {
    case Greeting(greeter) => log.info(s"I was greeted by $greeter.")
    case Goodbye => {
      log.info("Someone said goodbye to me.")
      context.stop(self)
    }
  }

}