package com.bmesta.engine.views

import akka.actor.Actor.Receive
import akka.actor.ActorLogging
import akka.persistence.PersistentView
import com.bmesta.engine.events.CaseCreated
import com.bmesta.engine.model.Case

/**
  * @author Baptiste Mesta.
  */
class CasesView extends PersistentView with ActorLogging{
  override def viewId: String = "case-view"

  val cases = collection.mutable.Map[String, Case]()

  override def receive: Receive = {
    case CaseCreated(aCase) => cases.put(aCase.name,aCase); log.warning(s"added a case on the view with name $aCase.name")
    case GetCase(name) =>sender() ! cases.get(name); log.warning(s"asked a case with naem $name")
  }

  override def persistenceId: String = "case-created"
}

case class GetCase(name: String)
