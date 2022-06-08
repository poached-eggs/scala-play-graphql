package services

import dao.GraphqlDao

import scala.concurrent.ExecutionContext

object ServiceFactory {

  def generate[A](dao: GraphqlDao[A])(implicit ec: ExecutionContext) =
    new GraphqlServiceImpl[A](dao)

}
