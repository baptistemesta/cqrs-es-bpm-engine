package com.bmesta.engine.events

import akka.actor.ActorLogging
import akka.persistence.PersistentActor
import com.bmesta.engine.model.Case


case class CaseCreated(aCase: Case)

case class CreateCase(aCase: Case)

class CaseActor extends PersistentActor with ActorLogging {
  override def receiveRecover: Receive = {
    case CaseCreated(aCase) => log.info(s"received recover of case ${aCase.name}")
  }

  override def receiveCommand: Receive = {
    case CreateCase(aCase) => log.info(s"created case ${aCase.name}"); persist(CaseCreated(aCase))(e => log.info(s"persisted $e"))
  }

  override def persistenceId: String = "case-created"
}

