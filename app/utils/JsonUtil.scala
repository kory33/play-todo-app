package utils

import play.api.libs.json.JsValue

object JsonUtil {

  implicit class SearchableJsValue(private val json: JsValue) {
    def getStringAt(fieldName: String) = (json \ fieldName).asOpt[String]
  }

  implicit class SearchableOptionJsValue(private val json: Option[JsValue]) {
    def getStringAt(fieldName: String) = json.flatMap { v => v.getStringAt(fieldName) }
  }

}
