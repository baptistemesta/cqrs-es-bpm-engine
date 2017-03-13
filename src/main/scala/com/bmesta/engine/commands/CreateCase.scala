package com.bmesta.engine.commands

import akka.persistence.{AbstractPersistentActor, PersistentActor}
import com.bmesta.engine.events.CaseCreated
import com.bmesta.engine.model.Case


/**
  * @author Baptiste Mesta.
  */
case class CreateCase(aCase: Case)

class CreateCaseActor extends AbstractPersistentActor {
  override def receiveRecover: Receive = {
    case CreateCase(aCase) =>  publish(aCase);
  }

  private def publish(aCase: Case) = {
    getContext().system.eventStream.publish(CaseCreated(aCase))
  }

  override def receiveCommand: Receive = {
    case aCase: Case => persist(CaseCreated(aCase)); publish(aCase)
  }

  override def persistenceId: String = "case-created"
}

