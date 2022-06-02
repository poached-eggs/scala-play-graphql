package services

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@Singleton
class Service @Inject() (
    env: play.Environment
  )(implicit
    ec: ExecutionContext
  ) {

  val graphqlService = new GraphqlService()

}
