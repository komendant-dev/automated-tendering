ThisBuild / scalaVersion := "2.13.14"

ThisBuild / version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """digis-scala-test""",
    libraryDependencies ++= Seq(
      guice,
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test
    )
  )

libraryDependencies += "com.github.tototoshi" %% "scala-csv" % "2.0.0"