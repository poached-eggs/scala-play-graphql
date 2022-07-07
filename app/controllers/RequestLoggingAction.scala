package controllers

import com.google.inject.{Inject, Singleton}
import models.RequestMetadata
import play.api.mvc._
import services.Service

import scala.concurrent.{ExecutionContext, Future}

class LoggedRequest[A](request: Request[A])
    extends WrappedRequest[A](request)

// Logs request metadata to a DB.
// This is useful for analysis purposes, but probably expensive with high traffic.
@Singleton
class RequestLoggingAction @Inject() (
    val parser: BodyParsers.Default,
    service: Service
  )(implicit
    val executionContext: ExecutionContext
  ) extends ActionBuilder[LoggedRequest, AnyContent] {

  // TODO: Clean up code!
  override def invokeBlock[A](request: Request[A], block: LoggedRequest[A] => Future[Result]): Future[Result] = {
    val requestMetadata = RequestMetadata.make(request)
    for {
      result <- block(new LoggedRequest(request))
      _ = service.requestMetadataService.insert(requestMetadata)
    } yield result

  }
}
