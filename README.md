# scala-play-graphql
Playing around with Play and GraphQL in scala.

Following the tutorial from [sangria-graphql](https://sangria-graphql.github.io/getting-started/)
and [Sangria Playground](https://github.com/sangria-graphql/sangria-playground).


## Running locally

```
sbt clean compile run
```

Hit the page at `locahost:9000`

## Submitting a GraphQL request

Check all the available routes in the routes file.

You can hit `/team` with the body:
```
query MyTeam {
    team(id: "1") {
        name
        country
        image(size: 500) {
            width, height, url
        }
    }
    teams {
        name
    }
}
```

Or using curl:
```
curl --request POST \
  --url http://localhost:9000/team \
  --header 'Content-Type: application/json' \
  --data '{"query":"query MyTeam {\n\tteam(id: \"1\") {\n\t\tname\n\t\tcountry\n\t\timage(size: 500) {\n\t\t\twidth, height, url\n\t\t}\n\t}\n\tteams {\n\t\tname\n\t}\n}","operationName":"MyTeam"}'
```

Should return:
```
{
	"data": {
		"team": {
			"name": "FC Barcelona",
			"country": "Spain",
			"image": {
				"width": 500,
				"height": 500,
				"url": "//cdn.com/500/1.jpg"
			}
		},
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
```

## TODOs
- Dockerize the application
- Use `docker-compose` to build and run app locally
- Add DB with MySQL and store data there instead with migrations
- Get POST with query parameter to work
- Add e-2-e test

## References
- [sangria-graphql](https://sangria-graphql.github.io/getting-started/)
- [scala-graphql-api-example](https://sysgears.com/articles/how-to-create-a-graphql-api-with-scala-and-sangria/)
- [sangria-playground](https://github.com/sangria-graphql/sangria-playground)