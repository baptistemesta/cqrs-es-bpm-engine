package com.bmesta.engine.views

import akka.actor.{Actor, ActorLogging}
import akka.persistence.query.PersistenceQuery
import akka.persistence.query.journal.leveldb.scaladsl.LeveldbReadJournal
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import com.bmesta.engine.events.CaseCreated
import com.bmesta.engine.model.Case

/**
  * @author Baptiste Mesta.
  */
class CasesView extends Actor with ActorLogging {

  implicit val materializer = ActorMaterializer()


  private def start() {
    val queries = PersistenceQuery(context.system).readJournalFor[LeveldbReadJournal](LeveldbReadJournal.Identifier)
    queries.eventsByPersistenceId("case-created", 0L, Long.MaxValue).map(e => e.event.asInstanceOf[CaseCreated].aCase).to(Sink.actorRef(this.self,{}))
  }

  override def receive: Receive = {
    case "start" => start(); log.info("started query")
    case GetCase(name) =>  log.info(s"asked a case with name $name")
    case GetAllCases => log.info("ask all cases")
    case aCase: Case => log.info(s"view received $aCase")
  }
}


case class GetCase(name: String)

case class GetAllCases(name: String)
