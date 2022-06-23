package dao

import anorm.Macro.ColumnNaming
import anorm.{Macro, RowParser}
import models.Player

import scala.concurrent.Future

trait PlayerDao {
  def getPlayers: Future[List[Player]]
  def getPLayer(id: String): Future[Option[Player]]
  def getPLayersByNationality(nationality: String): Future[List[Player]]
}

class PlayerDaoImpl(implicit db: PostgresDB) extends DimensionDao with PlayerDao {

  implicit lazy val teamParser: RowParser[Player] = Macro.namedParser[Player](ColumnNaming.SnakeCase)

  override val TableName: String = "player"
  val FILTER_BY_NATIONALITY = s"$SELECT_ALIAS.nationality={nationality}"

  override val SELECT_RECORDS: String =
    s""" select
       | id::varchar, first_name, last_name, field_position, nationality
       | from $TableName $SELECT_ALIAS
       |""".stripMargin

  override def getPlayers: Future[List[Player]] = db.getRecordList[Player](SELECT_RECORDS)

  override def getPLayer(id: String): Future[Option[Player]] =
    db.getRecord[Player](
      SELECT_RECORDS + QueryUtils.makeSingleWhereClause(SINGLE_RECORD),
      Symbol("id") -> id
    )

  override def getPLayersByNationality(nationality: String): Future[List[Player]] =
    db.getRecordList[Player](
      SELECT_RECORDS + QueryUtils.makeSingleWhereClause(FILTER_BY_NATIONALITY),
      Symbol("nationality") -> nationality
    )
}
