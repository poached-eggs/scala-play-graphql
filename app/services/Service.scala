package services

import dao.{GraphQlRepo, PlayerDaoImpl, PlayerRepo, PostgresDB, RequestMetadataDaoImpl, TeamDaoImpl, TeamRepo}

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@Singleton
class Service @Inject() (
    env: play.Environment
  )(implicit
    graphQlDb: PostgresDB,
    ec: ExecutionContext
  ) {

  private val requestMetadataDao = new RequestMetadataDaoImpl(debugMode = true)
  val requestMetadataService = new RequestMetadataServiceImpl(requestMetadataDao)

  implicit val teamDaoImpl: TeamDaoImpl = new TeamDaoImpl()
  implicit val playerDaoImpl: PlayerDaoImpl = new PlayerDaoImpl()
  val teamService: GraphqlServiceImpl[TeamRepo] = ServiceFactory.generate(GraphQlRepo.genTeamRepo)
  val playerService: GraphqlServiceImpl[PlayerRepo] = ServiceFactory.generate(GraphQlRepo.genPlayerRepo)

}
