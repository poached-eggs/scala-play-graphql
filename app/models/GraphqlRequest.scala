package models

import play.api.libs.json.JsObject

case class GraphqlRequest(
    query: String,
    operation: Option[String],
    variables: Option[JsObject]
  )
