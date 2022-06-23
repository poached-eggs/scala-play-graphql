package dao

import anorm.{Macro, RowParser}
import anorm.Macro.ColumnNaming
import models.Team

import scala.concurrent.Future

trait TeamDao {
  def getTeams: Future[List[Team]]
  def getTeam(id: String): Future[Option[Team]]
}

class TeamDaoImpl(implicit db: PostgresDB) extends DimensionDao with TeamDao {

  implicit lazy val teamParser: RowParser[Team] = Macro.namedParser[Team](ColumnNaming.SnakeCase)
  override val TableName = "team"

  override val SELECT_RECORDS: String =
    s""" select
       | id::varchar, name, country
       | from $TableName $SELECT_ALIAS
       |""".stripMargin

  override def getTeams: Future[List[Team]] = db.getRecordList[Team](SELECT_RECORDS)

  override def getTeam(id: String): Future[Option[Team]] =
    db.getRecord[Team](
      SELECT_RECORDS + QueryUtils.makeSingleWhereClause(SINGLE_RECORD),
      Symbol("id") -> id
    )

}
