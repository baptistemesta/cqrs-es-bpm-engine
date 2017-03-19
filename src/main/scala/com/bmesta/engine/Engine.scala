package com.bmesta.engine

import scala.concurrent.duration._
import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.pattern.ask
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.bmesta.engine.events.{CaseActor, CreateCase}
import com.bmesta.engine.model.Case
import com.bmesta.engine.views.{CasesView, GetCase}
import com.typesafe.config.ConfigFactory

import scala.util.{Failure, Success, Try}

/**
  * @author Baptiste Mesta.
  */
object Engine extends App {

  val config = ConfigFactory.load()
  val host = config.getString("http.host")
  val port = config.getInt("http.port")

  implicit val system = ActorSystem("Engine")


  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher
  implicit val timeout = Timeout(10 seconds)

  val caseActor = system.actorOf(Props[CaseActor], "caseActor")
  val caseView = system.actorOf(Props[CasesView], "caseView")


  val api = new CaseApi().casesRoutes(caseActor, caseView)

  Http().bindAndHandle(handler = api, interface = host, port = port) map { binding =>
    println(s"REST interface bound to ${binding.localAddress}") } recover { case ex =>
    println(s"REST interface could not bind to $host:$port", ex.getMessage)
  }


  caseActor ! CreateCase(Case("myFirstCase","desc"))

  (caseView ? GetCase("myFirstCase")).mapTo[Case].onComplete({
    case Success(aCase) => println(s"found $aCase")
    case Failure(_) => println("not found")
  })
}

