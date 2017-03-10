package com.bmesta.engine.serializers

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.bmesta.engine.entities.{Question, QuestionUpdate}
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

/**
  * @author Baptiste Mesta.
  */
trait QuestionJSonSupport extends SprayJsonSupport with DefaultJsonProtocol{
  implicit val questionFormat: RootJsonFormat[Question] = jsonFormat3(Question)
  implicit val questionUpdate: RootJsonFormat[QuestionUpdate] = jsonFormat2(QuestionUpdate)

}
