val scala2Version = "2.13.6"
val scala3Version = "3.0.1"
val zioHttpVersion = "1.0.0.0-RC17"
//val zioHttpVersion = "1.0.0.0-RC15+7-54a6202a-SNAPSHOT"
val zioVersion = "1.0.11"
//val zioVersion = "1.0.5+99-0699c11e-SNAPSHOT"
val yahooFinanceApiVersion = "3.15.0"
val slf4jVersion = "1.7.32"

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala3-cross",
    version := "0.1.0",

    libraryDependencies ++= Seq(
      "io.suzaku" %% "boopickle" % "1.4.0",
      "com.novocode" % "junit-interface" % "0.11" % Test,
      "com.yahoofinance-api" % "YahooFinanceAPI" % yahooFinanceApiVersion,
      "dev.zio" %% "zio-streams" % zioVersion,
      "io.d11" %% "zhttp" % zioHttpVersion,
      "org.slf4j" % "slf4j-api" % slf4jVersion
    ),
    // To make the default compiler and REPL use Dotty
    scalaVersion := scala3Version,
    // To cross compile with Scala 3 and Scala 2
    crossScalaVersions := Seq(scala3Version, scala2Version)
  )
