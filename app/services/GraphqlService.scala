package services

import dao.ProductRepo
import models.{GraphqlRequest, SchemaDefinitions}
import play.api.libs.json.{JsObject, JsString, JsValue, Json}
import play.api.mvc.Request
import sangria.execution.Executor
import sangria.marshalling.playJson.PlayJsonResultMarshaller.Node
import sangria.parser.QueryParser
import sangria.marshalling.playJson._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class GraphqlService(implicit ec: ExecutionContext) {

  def parseRequest(request: Request[JsValue]): GraphqlRequest = {
    val query = (request.body \ "query").as[String]
    val operation = (request.body \ "operationName").asOpt[String]
    val variables = (request.body \ "variables").toOption.flatMap {
      case JsString(vars) => Some(parseVariables(vars))
      case obj: JsObject  => Some(obj)
      case _              => None
    }
    GraphqlRequest(query, operation, variables)
  }

  def executeQuery(
      query: String,
      variables: Option[JsObject],
      operation: Option[String]
    ): Future[Node] =
    QueryParser.parse(query) match {
      case Success(queryAst) =>
        Executor
          .execute(
            schema = SchemaDefinitions.ProductSchema,
            queryAst = queryAst,
            userContext = new ProductRepo,
            operationName = operation,
            variables = variables.getOrElse(Json.obj())
          )
      case Failure(error) => throw error
    }

  private def parseVariables(variables: String): JsObject =
    if (variables.trim == "" || variables.trim == "null") Json.obj() else Json.parse(variables).as[JsObject]

}
