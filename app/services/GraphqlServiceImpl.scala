package services

import dao.GraphqlDao
import models.GraphqlRequest
import play.api.libs.json.{JsObject, JsString, JsValue, Json}
import play.api.mvc.Request
import sangria.execution.Executor
import sangria.marshalling.playJson.PlayJsonResultMarshaller.Node
import sangria.parser.QueryParser
import sangria.marshalling.playJson._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

trait GraphqlService {
  def parseRequest(request: Request[JsValue]): GraphqlRequest
  def executeQuery(
      query: String,
      variables: Option[JsObject],
      operation: Option[String]
    ): Future[Node]
}

class GraphqlServiceImpl[A](
    graphqlDao: GraphqlDao[A]
  )(implicit
    ec: ExecutionContext
  ) extends GraphqlService {

  override def parseRequest(request: Request[JsValue]): GraphqlRequest = {
    val query = (request.body \ "query").as[String]
    val operation = (request.body \ "operationName").asOpt[String]
    val variables = (request.body \ "variables").toOption.flatMap {
      case JsString(vars) => Some(parseVariables(vars))
      case obj: JsObject  => Some(obj)
      case _              => None
    }
    GraphqlRequest(query, operation, variables)
  }

  override def executeQuery(
      query: String,
      variables: Option[JsObject],
      operation: Option[String]
    ): Future[Node] =
    QueryParser.parse(query) match {
      case Success(queryAst) =>
        Executor
          .execute(
            schema = graphqlDao.schema,
            queryAst = queryAst,
            userContext = graphqlDao.dao,
            operationName = operation,
            variables = variables.getOrElse(Json.obj())
          )
      case Failure(error) => throw error
    }

  private def parseVariables(variables: String): JsObject =
    if (variables.trim == "" || variables.trim == "null") Json.obj() else Json.parse(variables).as[JsObject]
}
