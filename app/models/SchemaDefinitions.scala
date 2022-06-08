package models

import dao.{PlayerRepo, TeamRepo}
import sangria.schema._
import sangria.macros.derive._

object SchemaDefinitions {

  // ============================================================
  // Models Types

  // Derive a GraphQL definition based in a Scala case class
  implicit val PictureType: ObjectType[Unit, Image] =
    deriveObjectType[Unit, Image](
      ObjectTypeDescription("Image of the entity"),
      DocumentField("url", "Picture CDN URL")
    )

  val IdentifiableType: InterfaceType[Unit, Identifiable] = InterfaceType(
    "Identifiable",
    "Entity that can be identified",
    fields[Unit, Identifiable](
      Field("id", StringType, resolve = _.value.id)
    )
  )

  val TeamType: ObjectType[Unit, Team] =
    deriveObjectType[Unit, Team](
      Interfaces(IdentifiableType),
      IncludeMethods("image")
    )

  val PlayerType: ObjectType[Unit, Player] =
    deriveObjectType[Unit, Player](
      Interfaces(IdentifiableType),
      IncludeMethods(
        "fullName",
        "fullDescription"
      )
    )

  // ============================================================
  // Query Type

  val Id: Argument[String] = Argument("id", StringType)
  val Nationality: Argument[String] = Argument("nationality", StringType)

  val TeamQueryType: ObjectType[TeamRepo, Unit] = ObjectType(
    "TeamQuery",
    fields[TeamRepo, Unit](
      Field(
        name = "team",
        fieldType = OptionType(TeamType),
        description = Some("Returns a team with specific `id`."),
        arguments = Id :: Nil,
        resolve = c => c.ctx.team(c arg Id)
      ),
      Field(
        name = "teams",
        fieldType = ListType(TeamType),
        description = Some("Returns a list of all teams."),
        resolve = _.ctx.teams
      )
    )
  )

  val PlayerQueryType: ObjectType[PlayerRepo, Unit] = ObjectType(
    "PlayerQuery",
    fields[PlayerRepo, Unit](
      Field(
        name = "player",
        fieldType = OptionType(PlayerType),
        description = Some("Returns a player with specific `id`."),
        arguments = Id :: Nil,
        resolve = c => c.ctx.player(c arg Id)
      ),
      Field(
        name = "players",
        fieldType = ListType(PlayerType),
        description = Some("Returns a list of all available players."),
        resolve = _.ctx.players
      ),
      Field(
        name = "playersByNationality",
        fieldType = ListType(PlayerType),
        description = Some("Returns a list players for a given nationality."),
        arguments = Nationality :: Nil,
        resolve = c => c.ctx.playersByNationality(c arg Nationality)
      )
    )
  )

  // ============================================================
  // Schema(s)
  val TeamSchema: Schema[TeamRepo, Unit] = Schema(TeamQueryType)
  val PlayerSchema: Schema[PlayerRepo, Unit] = Schema(PlayerQueryType)

}
