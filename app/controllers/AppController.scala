package controllers

import javax.inject._
import play.api.libs.json._
import play.api.mvc._
import sangria.execution._
import sangria.marshalling.playJson._
import services.{GraphqlService, Service}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AppController @Inject() (
    protected val service: Service,
    cc: ControllerComponents,
    requestLoggingAction: RequestLoggingAction,
    implicit
    val ec: ExecutionContext
  ) extends AbstractController(cc) {

  // Instead of calling "Action.async(parse.json)", we use our own action, which will log request metadata to a DB.
  // This is useful for analysis purposes, but probably expensive with high traffic.
  def teams: Action[JsValue] = requestLoggingAction.async(parse.json) { request =>
    dispatchQuery(service.teamService, request)
      .map(Ok(_))
      .recover(handleQueryExecutionError(_))
  }

  def players: Action[JsValue] = requestLoggingAction.async(parse.json) { request =>
    dispatchQuery(service.playerService, request)
      .map(Ok(_))
      .recover(handleQueryExecutionError(_))
  }

  def dispatchQuery(graphqlService: GraphqlService, request: Request[JsValue]): Future[JsValue] = {
    val graphqlRequest = graphqlService.parseRequest(request)
    val query = graphqlRequest.query
    val operation = graphqlRequest.operation
    val variables = graphqlRequest.variables
    graphqlService
      .executeQuery(query, variables, operation)
  }

  private def handleQueryExecutionError(e: Throwable): Result = e match {
    case error: QueryAnalysisError => BadRequest(error.resolveError)
    case error: ErrorWithResolver  => InternalServerError(error.resolveError)
    case error                     => BadRequest(Json.obj("error" -> error.getMessage))
  }

}
