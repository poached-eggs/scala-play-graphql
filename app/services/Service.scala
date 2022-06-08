package services

import dao.{DaoFactory, PlayerRepo, TeamRepo}

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@Singleton
class Service @Inject() (
    env: play.Environment
  )(implicit
    ec: ExecutionContext
  ) {

  val teamService: GraphqlServiceImpl[TeamRepo] = ServiceFactory.generate(DaoFactory.genTeamDao)
  val playerService: GraphqlServiceImpl[PlayerRepo] = ServiceFactory.generate(DaoFactory.genPlayerDao)

}
