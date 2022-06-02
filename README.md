# scala-play-graphql
Playing around with Play and GraphQL in scala.

Following the tutorial from the official sangria-graphql [docs](https://sangria-graphql.github.io/getting-started/).


## Running locally

```
sbt clean compile run
```

Hit the page at `locahost:9000`

## Submitting a GraphQL request

You can hit `/graphqlBody` with the body:
```
{
	"query": "query MyProduct { product(id: \"2\") { name description picture(size: 500) {width,height,url} } products { name } }"
}
```

Or using curl:
```
curl --request POST \
  --url http://localhost:9000/graphqlBody \
  --header 'Content-Type: application/json' \
  --data '{
	"query": "query MyProduct { product(id: \"2\") { name description picture(size: 500) {width,height,url} } products { name } }"
    }
    '
```

Should return:
```
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
    },
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
```

## TODOs
- Change to soccer players because more fun
- Dockerize the application
- Use `docker-compose`
- Add DB with MySQL and store data there instead with migrations
- Get POST with query parameter to work

## References
- [sangria-graphql](https://sangria-graphql.github.io/getting-started/)
- [scala-graphql-api-example](https://sysgears.com/articles/how-to-create-a-graphql-api-with-scala-and-sangria/)