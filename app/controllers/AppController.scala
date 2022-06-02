package controllers

import javax.inject._
import play.api.libs.json._
import play.api.mvc._
import sangria.execution._
import sangria.marshalling.playJson._
import services.Service

import scala.concurrent.ExecutionContext

@Singleton
class AppController @Inject() (
    protected val service: Service,
    cc: ControllerComponents,
    implicit
    val ec: ExecutionContext
  ) extends AbstractController(cc) {

  def graphqlBody: Action[JsValue] = Action.async(parse.json) { request =>
    val graphqlRequest = service.graphqlService.parseRequest(request)
    val query = graphqlRequest.query
    val operation = graphqlRequest.operation
    val variables = graphqlRequest.variables
    service
      .graphqlService
      .executeQuery(query, variables, operation)
      .map(Ok(_))
      .recover(handleQueryExecutionError(_))
  }

  private def handleQueryExecutionError(e: Throwable): Result = e match {
    case error: QueryAnalysisError => BadRequest(error.resolveError)
    case error: ErrorWithResolver  => InternalServerError(error.resolveError)
    case error                     => BadRequest(Json.obj("error" -> error.getMessage))
  }

}
