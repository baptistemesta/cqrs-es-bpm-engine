package com.bmesta.engine.events

import akka.actor.Actor
import com.bmesta.engine.model.Case

/**
  * @author Baptiste Mesta.
  */
case class CaseCreated(aCase : Case)

class CaseCreatedActor extends Actor{
  override def receive: Receive = {

  }
}