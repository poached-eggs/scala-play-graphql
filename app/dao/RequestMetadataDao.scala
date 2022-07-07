package dao

import anorm.RowParser
import anorm.SqlParser._
import models.RequestMetadata
import play.api.Logger

import java.util.UUID
import scala.concurrent.Future

trait RequestMetadataDao {
  def insert(requestMeta: RequestMetadata): Future[UUID]
}

class RequestMetadataDaoImpl(debugMode: Boolean)(implicit db: PostgresDB) extends DimensionDao with RequestMetadataDao {

  implicit private val logger: Logger = Logger(this.getClass)

  private val pkParser: RowParser[UUID] = get[UUID](1)
  private val genIdFunction = "uuid_generate_v4()"
  private val Cols: String = List("id, datetime", "uri", "body").mkString(",")

  override val TableName: String = "request_metadata"
  override val SELECT_RECORDS: String =
    s""" SELECT
       | id::varchar, $Cols
       | FROM $TableName $SELECT_ALIAS
       |""".stripMargin

  val INSERT_RECORD: String =
    s""" INSERT INTO $TableName ($Cols)
       | VALUES ($genIdFunction, {datetime}, {uri}, {body})
       | RETURNING id
       |""".stripMargin

  override def insert(requestMeta: RequestMetadata): Future[UUID] = {

    if (debugMode) {
      logger.info(
        s"Logging request [time= ${requestMeta.datetime}] [uri= ${requestMeta.uri}] [body= ${requestMeta.body}...]"
      )
    }

    db.insertRecord(
      INSERT_RECORD,
      Symbol("datetime") -> requestMeta.datetime,
      Symbol("uri") -> requestMeta.uri,
      Symbol("body") -> requestMeta.body
    )(pkParser)
  }
}
