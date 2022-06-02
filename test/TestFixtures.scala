object TestFixtures {
  val ProductQuery: String =
    """
        query MyProduct {
          product(id: "2") {
            name
            description
            picture(size: 500) {
              width, height, url
            }
          }
        }
      """

  val ProductListQuery: String =
    """
        query MyProduct {
          products {
            name
          }
        }
      """

  val ExpectedProductResult =
    """
        {
          "data": {
              "product": {
                  "name": "Health Potion",
                  "description": "+50 HP",
                  "picture": {
                      "width": 500,
                      "height": 500,
                      "url": "//cdn.com/500/2.jpg"
                  }
              }
          }
        }
        """

  val ExpectedProductListResult =
    """
        {
          "data": {
              "products": [
                  {
                      "name": "Cheesecake"
                  },
                  {
                      "name": "Health Potion"
                  }
              ]
          }
        }
        """
}
