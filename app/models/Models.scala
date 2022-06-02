package models

case class Picture(
    width: Int,
    height: Int,
    url: Option[String]
  )

trait Identifiable {
  def id: String
}

case class Product(
    id: String,
    name: String,
    description: String
  ) extends Identifiable {
  def picture(size: Int): Picture =
    Picture(width = size, height = size, url = Some(s"//cdn.com/$size/$id.jpg"))
}
