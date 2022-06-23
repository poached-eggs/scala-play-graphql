package dao

import models.{Player, SchemaDefinitions, Team}
import sangria.schema.Schema

import scala.concurrent.Future

case class GraphQlRepo[A](
    dao: A,
    schema: Schema[A, Unit]
  )

class TeamRepo(teamsDb: TeamDao) {
  def team(id: String): Future[Option[Team]] = teamsDb.getTeam(id)
  def teams: Future[List[Team]] = teamsDb.getTeams
}

class PlayerRepo(playersDb: PlayerDao) {
  def player(id: String): Future[Option[Player]] = playersDb.getPLayer(id)
  def players: Future[List[Player]] = playersDb.getPlayers
  def playersByNationality(nationality: String): Future[List[Player]] = playersDb.getPLayersByNationality(nationality)
}

object GraphQlRepo {

  def genTeamRepo(implicit teamDao: TeamDao): GraphQlRepo[TeamRepo] =
    GraphQlRepo(new TeamRepo(teamDao), SchemaDefinitions.TeamSchema)

  def genPlayerRepo(implicit playerDao: PlayerDao): GraphQlRepo[PlayerRepo] =
    GraphQlRepo(new PlayerRepo(playerDao), SchemaDefinitions.PlayerSchema)
}
