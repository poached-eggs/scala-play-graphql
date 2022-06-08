name := """scala-play-graphql"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.8"

val circeVersion = "0.14.1"

libraryDependencies ++= Seq(
  guice,
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,

  // GraphQL
  "org.sangria-graphql" %% "sangria-play-json" % "2.0.1",
  "org.sangria-graphql" %% "sangria" % "2.0.0",

  // Circe
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "io.circe" %% "circe-optics" % circeVersion
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
