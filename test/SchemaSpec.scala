import dao.{DaoFactory, PlayerRepo, TeamRepo}
import org.scalatest.funsuite.AsyncFunSuite
import org.scalatest.matchers.must.Matchers
import play.api.libs.json.{JsValue, Json}
import services.{GraphqlServiceImpl, ServiceFactory}

class SchemaSpec extends AsyncFunSuite with Matchers {

  val teamService: GraphqlServiceImpl[TeamRepo] = ServiceFactory.generate(DaoFactory.genTeamDao)
  val playerService: GraphqlServiceImpl[PlayerRepo] = ServiceFactory.generate(DaoFactory.genPlayerDao)

  test("returns team details for a given id") {
    val query = TestFixtures.TeamQuery
    val expectedResult = TestFixtures.ExpectedTeamResult
    val futResult = teamService.executeQuery(query, variables = None, operation = None)
    futResult.map(result => result mustBe expectedResult.toJson)
  }

  test("returns list of all teams") {
    val query = TestFixtures.TeamListQuery
    val expectedResult = TestFixtures.ExpectedTeamListResult
    val result = teamService.executeQuery(query, variables = None, operation = None)
    result.map(result => result mustBe expectedResult.toJson)
  }

  test("returns player details for a given id") {
    val query = TestFixtures.PlayerQuery
    val expectedResult = TestFixtures.ExpectedPlayerResult
    val futResult = playerService.executeQuery(query, variables = None, operation = None)
    futResult.map(result => result mustBe expectedResult.toJson)
  }

  implicit private class StringUtils(s: String) {
    def toJson: JsValue = Json.parse(s)
  }

}
