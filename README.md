# scala-play-graphql
Playing around with Play and GraphQL in scala.

Following the tutorial from [sangria-graphql](https://sangria-graphql.github.io/getting-started/)
and [Sangria Playground](https://github.com/sangria-graphql/sangria-playground).


## Running locally

Few environment variables are required:
```
HTTP_SECRET_KEY
DB_HOST
DB_USERNAME
DB_PASSWORD
```

### With sbt

```
sbt clean compile run
```

You also need to run an instance of PosgtreSQL locally with the database `docker`.

Hit the page at `locahost:9000/health-check`.

### With Docker

Assuming you have Docker daemon running locally:
```
docker-compose build
docker-compose up
```
Hit the page at `locahost:9000/health-check`

You can also inspect the database:
```
docker exec -it db psql -U postgres
\c docker
\dt
```
Which should display:
```
                List of relations
 Schema |         Name         | Type  |  Owner
--------+----------------------+-------+----------
 public | play_evolutions      | table | postgres
 public | play_evolutions_lock | table | postgres
 public | player               | table | postgres
 public | team                 | table | postgres
(4 rows)
```

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
				"url": "//foo.bar/500/1.jpg"
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
- Store & retrieve data from DB
- Get POST with query parameter to work
- Add e-2-e test
- Github actions to run tests

## References
- [sangria-graphql](https://sangria-graphql.github.io/getting-started/)
- [scala-graphql-api-example](https://sysgears.com/articles/how-to-create-a-graphql-api-with-scala-and-sangria/)
- [sangria-playground](https://github.com/sangria-graphql/sangria-playground)