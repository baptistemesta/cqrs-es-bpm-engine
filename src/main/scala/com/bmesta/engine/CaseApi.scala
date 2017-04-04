package com.bmesta.engine

import java.util.concurrent.TimeUnit

import akka.actor.ActorRef
import akka.http.scaladsl.server.{Directives, Route}
import akka.pattern.ask
import akka.util.Timeout
import com.bmesta.engine.events.CreateCase
import com.bmesta.engine.model.Case
import com.bmesta.engine.serializers.CasesJSonSupport
import com.bmesta.engine.views.{GetAllCases, GetCase}

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.Duration

/**
  * @author Baptiste Mesta.
  */
class CaseApi extends Directives with CasesJSonSupport {

  implicit val timeout = Timeout(Duration.create(5, TimeUnit.SECONDS))


  def casesRoutes(createCase: ActorRef, casesView: ActorRef)(implicit ec: ExecutionContext): Route =

    path("cases") {
      post {
        entity(as[Case]) { aCase =>
          complete {
            createCase ! CreateCase(aCase)
            aCase
          }
        }
      } ~
        get {
          complete((casesView ? GetAllCases).mapTo[Map[String, Case]])
        }
    } ~
      path("case" / Segment) { name =>
        get {
          complete((casesView ? GetCase(name)).mapTo[Option[Case]])
        }
      }

}
