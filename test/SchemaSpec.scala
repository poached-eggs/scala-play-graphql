import dao.{PlayerDaoImpl, PlayerRepo, GraphQlRepo, TeamDaoImpl, TeamRepo}
import org.mockito.Mockito._
import org.scalatest.funsuite.AsyncFunSuite
import org.scalatest.matchers.must.Matchers
import org.scalatestplus.mockito.MockitoSugar
import play.api.libs.json.{JsValue, Json}
import services.{GraphqlServiceImpl, ServiceFactory}

import scala.concurrent.Future

class SchemaSpec extends AsyncFunSuite with Matchers with MockitoSugar {

  implicit val teamDaoImpl: TeamDaoImpl = mock[TeamDaoImpl]
  implicit val playerDaoImpl: PlayerDaoImpl = mock[PlayerDaoImpl]
  val teamService: GraphqlServiceImpl[TeamRepo] = ServiceFactory.generate(GraphQlRepo.genTeamRepo)
  val playerService: GraphqlServiceImpl[PlayerRepo] = ServiceFactory.generate(GraphQlRepo.genPlayerRepo)

  test("returns team details for a given id") {
    val teamId = "2"
    val query = TestFixtures.SingleTeam.genQuery(teamId)
    val expectedResult = TestFixtures.SingleTeam.ExpectedResult
    val stubbedRecord = Future(Option(TestFixtures.SingleTeam.StubbedRecord))
    when(teamDaoImpl.getTeam(teamId)).thenReturn(stubbedRecord)
    val futResult = teamService.executeQuery(query, variables = None, operation = None)
    futResult.map(result => result mustBe expectedResult.toJson)
  }

  test("returns list of all teams") {
    val query = TestFixtures.Teams.query
    val expectedResult = TestFixtures.Teams.ExpectedResult
    val stubbedRecord = Future(TestFixtures.Teams.StubbedRecord)
    val result = teamService.executeQuery(query, variables = None, operation = None)
    when(teamDaoImpl.getTeams).thenReturn(stubbedRecord)
    result.map(result => result mustBe expectedResult.toJson)
  }

  test("returns player details for a given id") {
    val playerId = "1"
    val query = TestFixtures.SinglePlayer.genQuery(playerId)
    val expectedResult = TestFixtures.SinglePlayer.ExpectedResult
    val stubbedRecord = Future(Option(TestFixtures.SinglePlayer.StubbedRecord))
    val futResult = playerService.executeQuery(query, variables = None, operation = None)
    when(playerDaoImpl.getPLayer(playerId)).thenReturn(stubbedRecord)
    futResult.map(result => result mustBe expectedResult.toJson)
  }

  implicit private class StringUtils(s: String) {
    def toJson: JsValue = Json.parse(s)
  }

}
