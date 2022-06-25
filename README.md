# scala-play-graphql

Sample service written in Play with scala and GraphQL.

This sample service:
- Stores few soccer players and team information.
- Serves clients with player and team information via endpoints (examples below)
- Uses a PostgreSQL database for persistent storage.
- Uses DB migrations to provision the schema and sample data in the database.
- Uses Github Actions to run automated tests on every PR.

First time using GraphQL, so  I followed the tutorial from [sangria-graphql](https://sangria-graphql.github.io/getting-started/)
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

Firstly, you need to run an instance of PosgtreSQL locally with a database named `docker`. Then you can run:
```console
sbt clean compile run
```

Hit the page at `locahost:9000/health-check`.

### With Docker

Assuming you have Docker daemon running locally:
```console
docker-compose build
docker-compose up
```
Hit the page at `locahost:9000/health-check`

You can also inspect the database:
```console
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

Check all the available routes in the `conf/routes` file.

You can get a list of teams by hitting `/team` with the body:
```
query MyTeam {
    teams {
        name id country
    }
}
```

Or using curl:
```console
curl --request POST \
  --url http://localhost:9000/team \
  --header 'Content-Type: application/json' \
  --data '{"query":"query MyTeam {\n\tteams {\n\t\tname id country\n\t}\n}","operationName":"MyTeam"}'
```

Should return (note the `id` values will differ since they are autogenerate during the bootstrap of the database):
```json
{
	"data": {
		"teams": [
			{
				"name": "F.C. Barcelona",
				"id": "ae8647c6-cc7b-4974-9037-62561f0a3c3f",
				"country": "Spain"
			},
			{
				"name": "Real Madrid",
				"id": "83a9514c-6c67-4b1c-b391-10b8357e1b0d",
				"country": "Spain"
			}
		]
	}
}
```

You can get use one of the team `id` values to search of that team info:
```
query MyTeam {
	team(id: "ae8647c6-cc7b-4974-9037-62561f0a3c3f") {
		name
		country
		image(size: 500) {
			width, height, url
		}
	}
}
```

To get:
```json
{
	"data": {
		"team": {
			"name": "F.C. Barcelona",
			"country": "Spain",
			"image": {
				"width": 500,
				"height": 500,
				"url": "//foo.bar/500/ae8647c6-cc7b-4974-9037-62561f0a3c3f.jpg"
			}
		}
	}
}
```

## TODOs
- Log events to a DB
- Get POST with query parameter to work
- Add e-2-e test

## References
- [sangria-graphql](https://sangria-graphql.github.io/getting-started/)
- [scala-graphql-api-example](https://sysgears.com/articles/how-to-create-a-graphql-api-with-scala-and-sangria/)
- [sangria-playground](https://github.com/sangria-graphql/sangria-playground)