package dao

import models.{Player, SchemaDefinitions, Team}
import sangria.schema.Schema

trait Dao

// TODO: Replace with persistent storage
class TeamRepo extends Dao {
  private val Teams = List(
    Team("1", "FC Barcelona", "Spain"),
    Team("2", "Juventus", "Italy")
  )
  def team(id: String): Option[Team] = Teams.find(_.id == id)
  def teams: List[Team] = Teams
}

// TODO: Replace with persistent storage
class PlayerRepo extends Dao {
  private val Players = List(
    Player("1", "Luis", "Figo", "RW", "Portugal"),
    Player("2", "Zinedine", "Zidane", "CAM", "France")
  )
  def player(id: String): Option[Player] = Players.find(_.id == id)
  def players: List[Player] = Players
  def playersByNationality(nationality: String): List[Player] = Players.filter(_.nationality == nationality)
}

case class GraphqlDao[A](
    dao: A,
    schema: Schema[A, Unit]
  )

object DaoFactory {
  private val teamRepo = new TeamRepo
  private val playerRepo = new PlayerRepo
  val genTeamDao: GraphqlDao[TeamRepo] = GraphqlDao(teamRepo, SchemaDefinitions.TeamSchema)
  val genPlayerDao: GraphqlDao[PlayerRepo] = GraphqlDao(playerRepo, SchemaDefinitions.PlayerSchema)
}
