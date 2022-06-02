package models

import dao.ProductRepo
import sangria.schema._
import sangria.macros.derive._

object SchemaDefinitions {

  // ============================================================
  // Models Types

  // Derive a GraphQL definition based in a Scala case class
  implicit val PictureType: ObjectType[Unit, Picture] =
    deriveObjectType[Unit, Picture](
      ObjectTypeDescription("The product picture"),
      DocumentField("url", "Picture CDN URL")
    )

  val IdentifiableType: InterfaceType[Unit, Identifiable] = InterfaceType(
    "Identifiable",
    "Entity that can be identified",
    fields[Unit, Identifiable](
      Field("id", StringType, resolve = _.value.id)
    )
  )

  val ProductType: ObjectType[Unit, Product] =
    deriveObjectType[Unit, Product](
      Interfaces(IdentifiableType),
      IncludeMethods("picture")
    )

  // ============================================================
  // Query Type

  val Id: Argument[String] = Argument("id", StringType)

  val QueryType: ObjectType[ProductRepo, Unit] = ObjectType(
    "Query",
    fields[ProductRepo, Unit](
      Field(
        name = "product",
        fieldType = OptionType(ProductType),
        description = Some("Returns a product with specific `id`."),
        arguments = Id :: Nil,
        resolve = c => c.ctx.product(c arg Id)
      ),
      Field(
        name = "products",
        fieldType = ListType(ProductType),
        description = Some("Returns a list of all available products."),
        resolve = _.ctx.products
      )
    )
  )

  // ============================================================
  // Schema
  val ProductSchema: Schema[ProductRepo, Unit] = Schema(QueryType)

}
