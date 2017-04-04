package com.bmesta.engine.events

import akka.actor.ActorLogging
import akka.persistence.PersistentActor
import com.bmesta.engine.model.Case


case class CaseCreated(aCase: Case)

case class CreateCase(aCase: Case)

class CaseActor extends PersistentActor with ActorLogging {
  override def receiveRecover: Receive = {
    case CaseCreated(aCase) =>
      log.info(s"received recover of case ${aCase.name}")
      context.system.eventStream.publish(CaseCreated(aCase))
  }

  override def receiveCommand: Receive = {
    case CreateCase(aCase) =>
      log.info(s"create case ${aCase.name}")
      persist(CaseCreated(aCase))(e => context.system.eventStream.publish(e))
  }

  override def persistenceId: String = "case-created"
}

