import models.{Player, Team}

object TestFixtures {

  object SingleTeam {
    def genQuery(id: String): String =
      s"""
        query MyTeam {
          team(id: "$id") {
            name
            country
            image(size: 500) {
              width, height, url
            }
          }
        }
      """
    val StubbedRecord: Team =
      Team("2", "Juventus", "Italy")
    val ExpectedResult =
      """
        {
          "data": {
              "team": {
                  "name": "Juventus",
                  "country": "Italy",
                  "image": {
                      "width": 500,
                      "height": 500,
                      "url": "//foo.bar/500/2.jpg"
                  }
              }
          }
        }
        """
  }

  object Teams {
    val query: String =
      """
        query MyTeam {
          teams {
            name country
          }
        }
      """
    val StubbedRecord: List[Team] =
      List(
        Team("1", "FC Barcelona", "Spain"),
        Team("2", "Juventus", "Italy")
      )
    val ExpectedResult =
      """
        {
          "data": {
              "teams": [
                  {
                      "name": "FC Barcelona",
                      "country": "Spain"
                  },
                  {
                      "name": "Juventus",
                      "country": "Italy"
                  }
              ]
          }
        }
        """
  }

  object SinglePlayer {
    def genQuery(id: String): String =
      s"""
        query MyPlayer {
          player(id: "$id") {
            firstName
            lastName
            fullName
            fullDescription
          }
        }
      """
    val StubbedRecord: Player =
      Player("1", "Luis", "Figo", "RW", "Portugal")
    val ExpectedResult =
      """
        {
          "data": {
              "player": {
                  "firstName": "Luis",
                  "lastName": "Figo",
                  "fullName": "Luis Figo",
                  "fullDescription": "Luis Figo (RW) (Portugal)"
              }
          }
        }
        """
  }

}
