package com.bmesta.engine.serializers

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.bmesta.engine.model.Case
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

/**
  * @author Baptiste Mesta.
  */
trait CasesJSonSupport extends SprayJsonSupport with DefaultJsonProtocol{
  implicit val caseFormat: RootJsonFormat[Case] = jsonFormat2(Case)

}
