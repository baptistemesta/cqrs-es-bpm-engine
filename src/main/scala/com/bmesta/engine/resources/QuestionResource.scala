package com.bmesta.engine.resources

import akka.http.scaladsl.server.{Directives, Route}
import com.bmesta.engine.entities.{Question, QuestionUpdate}
import com.bmesta.engine.serializers.QuestionJSonSupport
import com.bmesta.engine.services.QuestionService

/**
  * @author Baptiste Mesta.
  */
trait QuestionResource extends Directives with QuestionJSonSupport {

  val questionService: QuestionService


  def questionRoutes: Route = pathPrefix("question") {
    pathEnd {
      post {
        entity(as[Question]) { question =>
          rejectEmptyResponse {
            complete(questionService.createQuestion(question))
          }
        }
      }
    } ~
      path(Segment) { id =>
        get {
          rejectEmptyResponse {
            complete(questionService.getQuestion(id))
          }
        } ~
          put {
            entity(as[QuestionUpdate]) { update =>

              rejectEmptyResponse {
                complete(questionService.updateQuestion(id, update))
              }
            }
          } ~
          delete {

            rejectEmptyResponse {
              complete(questionService.deleteQuestion(id))
            }
          }
      }

  }
}
