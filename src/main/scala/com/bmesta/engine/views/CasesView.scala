package com.bmesta.engine.views

import akka.actor.{Actor, ActorLogging}
import akka.stream.ActorMaterializer
import com.bmesta.engine.events.CaseCreated
import com.bmesta.engine.model.Case

import scala.collection.mutable

/**
  * @author Baptiste Mesta.
  */
class CasesView extends Actor with ActorLogging {

  implicit val materializer = ActorMaterializer()
  val cases: mutable.Map[String, Case] = collection.mutable.Map[String, Case]()

  override def preStart: Unit = context.system.eventStream.subscribe(self, classOf[CaseCreated])

  override def receive: Receive = {
    case GetCase(name) => sender() ! cases.get(name)
    case GetAllCases => sender() ! cases.toMap
    case CaseCreated(aCase) =>
      log.info(s"view received $aCase")
      cases.put(aCase.name, aCase)
  }
}


case class GetCase(name: String)

case class GetAllCases(name: String)
