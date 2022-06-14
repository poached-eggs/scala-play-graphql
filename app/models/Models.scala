package models

trait Identifiable {
  def id: String
}

case class Image(
    width: Int,
    height: Int,
    url: Option[String]
  )

case class Team(
    id: String,
    name: String,
    country: String
  ) extends Identifiable {
  def image(size: Int): Image =
    Image(width = size, height = size, url = Some(s"//foo.bar/$size/$id.jpg"))
}

case class Player(
    id: String,
    firstName: String,
    lastName: String,
    fieldPosition: String,
    nationality: String
  ) extends Identifiable {
  val fullName: String = s"$firstName $lastName"
  val fullDescription: String = s"$fullName ($fieldPosition) ($nationality)"
}
