package dao

trait DimensionDao {
  val TableName: String
  val SELECT_RECORDS: String
  val SELECT_ALIAS: String = "x"
  val SINGLE_RECORD: String = s"$SELECT_ALIAS.id::text={id}"
}
