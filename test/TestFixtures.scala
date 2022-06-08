object TestFixtures {
  val TeamQuery: String =
    """
        query MyTeam {
          team(id: "2") {
            name
            country
            image(size: 500) {
              width, height, url
            }
          }
        }
      """

  val PlayerQuery: String =
    """
        query MyPlayer {
          player(id: "1") {
            firstName
            lastName
            fullName
            fullDescription
          }
        }
      """

  val TeamListQuery: String =
    """
        query MyTeam {
          teams {
            name
          }
        }
      """

  val ExpectedTeamResult =
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

  val ExpectedPlayerResult =
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

  val ExpectedTeamListResult =
    """
        {
          "data": {
              "teams": [
                  {
                      "name": "FC Barcelona"
                  },
                  {
                      "name": "Juventus"
                  }
              ]
          }
        }
        """
}
