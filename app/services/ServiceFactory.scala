package services

import dao.GraphQlRepo

import scala.concurrent.ExecutionContext

object ServiceFactory {

  def generate[A](dao: GraphQlRepo[A])(implicit ec: ExecutionContext) =
    new GraphqlServiceImpl[A](dao)

}
