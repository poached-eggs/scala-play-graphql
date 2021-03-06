name := """scala-play-graphql"""

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.8"

val circeVersion = "0.14.1"

libraryDependencies ++= Seq(
  guice,

  // GraphQL
  "org.sangria-graphql" %% "sangria-play-json" % "2.0.1",
  "org.sangria-graphql" %% "sangria" % "2.0.0",

  // Circe
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "io.circe" %% "circe-optics" % circeVersion,

  // Database Connection
  jdbc,
  "org.postgresql" % "postgresql" % "42.3.6",

  // Evolutions
  evolutions,

  // Anorm
  "org.playframework.anorm" %% "anorm" % "2.6.5",

  // Tests
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,
  "org.scalatestplus" %% "mockito-4-5" % "3.2.12.0" % "test" // mockito needed here since it was removed in scalatestplus-play 5.1.0
)

Test / javaOptions += "-Dconfig.file=conf/application.test.conf"

assembly / assemblyMergeStrategy := {
  case PathList("module-info.class", _*)                                             => MergeStrategy.discard
  case PathList("META-INF", _*)                                                      => MergeStrategy.discard
  case referenceOverrides if referenceOverrides.contains("reference-overrides.conf") => MergeStrategy.concat
  case a if a.contains("javax/activation")                                           => MergeStrategy.first
  case o =>
    val oldStrategy = (assembly / assemblyMergeStrategy).value
    oldStrategy(o)
}

// Skip scaladoc
Compile / doc / sources := Nil
Compile / packageDoc / publishArtifact := false

// Disable tests for faster builds (dev purposes)
//assembly / test := {}
