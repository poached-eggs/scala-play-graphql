import org.scalatest.funsuite.AsyncFunSuite
import org.scalatest.matchers.must.Matchers
import play.api.libs.json.{JsValue, Json}
import services.GraphqlService

class SchemaSpec extends AsyncFunSuite with Matchers {

  val graphqlService: GraphqlService = new GraphqlService()

  test("returns object details for a given id") {
    val query = TestFixtures.ProductQuery
    val expectedResult = TestFixtures.ExpectedProductResult
    val futResult = graphqlService.executeQuery(query, variables = None, operation = None)
    futResult.map(result => result mustBe expectedResult.toJson)
  }

  test("returns list of all available objects") {
    val query = TestFixtures.ProductListQuery
    val expectedResult = TestFixtures.ExpectedProductListResult
    val result = graphqlService.executeQuery(query, variables = None, operation = None)
    result.map(result => result mustBe expectedResult.toJson)
  }

  implicit private class StringUtils(s: String) {
    def toJson: JsValue = Json.parse(s)
  }

}
