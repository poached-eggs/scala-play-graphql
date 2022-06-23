package services

import dao.{PlayerDaoImpl, PlayerRepo, PostgresDB, GraphQlRepo, TeamDaoImpl, TeamRepo}

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@Singleton
class Service @Inject() (
    env: play.Environment
  )(implicit
    graphQlDb: PostgresDB,
    ec: ExecutionContext
  ) {

  implicit val teamDaoImpl: TeamDaoImpl = new TeamDaoImpl()
  implicit val playerDaoImpl: PlayerDaoImpl = new PlayerDaoImpl()
  val teamService: GraphqlServiceImpl[TeamRepo] = ServiceFactory.generate(GraphQlRepo.genTeamRepo)
  val playerService: GraphqlServiceImpl[PlayerRepo] = ServiceFactory.generate(GraphQlRepo.genPlayerRepo)

}
