package dao

import anorm._
import com.google.inject.Inject
import etc.DefaultDbExecutionContext
import play.api.db.Database

import scala.concurrent.{ExecutionContext, Future}

// ====================================================================================
// Database - Traits and Properties
// ====================================================================================
abstract class GenericAbstractDb(
    val db: Database,
    val dbEC: ExecutionContext
  )

trait GenericSelectDbTrait {
  this: GenericAbstractDb =>

  def getRecord[A](sql: String, args: NamedParameter*)(implicit parser: RowParser[A]): Future[Option[A]] = Future(
    db.withConnection(implicit conn => SQL(sql).on(args: _*).as(parser.singleOpt))
  )(dbEC)

  def getRecordList[A](sql: String, args: NamedParameter*)(implicit parser: RowParser[A]): Future[List[A]] = Future(
    db.withConnection(implicit conn => SQL(sql).on(args: _*).as(parser.*))
  )(dbEC)

}

// ====================================================================================
// Database - Implementation
// ====================================================================================
class PostgresDB @Inject() (db: Database, dbEC: DefaultDbExecutionContext)
    extends GenericAbstractDb(db, dbEC)
       with GenericSelectDbTrait

object QueryUtils {
  def makeSingleWhereClause(singleClause: String) = s"where $singleClause"
}
