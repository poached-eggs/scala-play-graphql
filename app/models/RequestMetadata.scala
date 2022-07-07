package models

import play.api.mvc.Request

import java.time.LocalDateTime

case class RequestMetadata(
    datetime: LocalDateTime,
    uri: String,
    body: String
  )

object RequestMetadata {
  private val RequestBodySizeToLog: Int = 30
  def make[A](request: Request[A]): RequestMetadata =
    RequestMetadata(
      datetime = LocalDateTime.now(),
      uri = request.uri,
      body = request.body.toString.take(RequestBodySizeToLog)
    )
}
