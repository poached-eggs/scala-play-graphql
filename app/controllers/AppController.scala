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
    implicit
    val ec: ExecutionContext
  ) extends AbstractController(cc) {

  def teams: Action[JsValue] = Action.async(parse.json) { request =>
    dispatchQuery(service.teamService, request)
      .map(Ok(_))
      .recover(handleQueryExecutionError(_))
  }

  def players: Action[JsValue] = Action.async(parse.json) { request =>
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
