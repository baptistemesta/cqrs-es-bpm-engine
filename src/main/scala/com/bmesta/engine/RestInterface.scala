package com.bmesta.engine

import akka.http.scaladsl.server.Route
import com.bmesta.engine.resources.QuestionResource
import com.bmesta.engine.services.QuestionService

import scala.concurrent.ExecutionContext

/**
  * @author Baptiste Mesta.
  */
trait RestInterface extends Resources {
  implicit def executionContext: ExecutionContext

  lazy val questionService = new QuestionService

  val routes: Route = questionRoutes
}

trait Resources extends QuestionResource
