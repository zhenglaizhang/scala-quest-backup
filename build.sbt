
val catsVersion = "0.9.0"
val shapelessVersion = "2.3.2"
val scalazVersion = "7.2.10"
val catsAll = "org.typelevel" %% "cats" % catsVersion
val shapelessAll = "com.chuusai" %% "shapeless" % shapelessVersion
val refinedVersion = "0.8.0"
val refined = List(
  "eu.timepit" %% "refined" % refinedVersion,
  "eu.timepit" %% "refined-pureconfig" % refinedVersion,  // optional, JVM-only
  "eu.timepit" %% "refined-scalacheck" % refinedVersion, // optional
  "eu.timepit" %% "refined-scalaz" % refinedVersion, // optional
  "eu.timepit" %% "refined-scodec" % refinedVersion // optional
)

//val macroParadise = compilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
//val kindProjector = compilerPlugin("org.spire-math" %% "kind-projector" % "0.6.3")
//val resetAllAttrs = "org.scalamacros" %% "resetallattrs" % "1.0.0-M1"

//val specs2Version = "3.6" // use the version used by discipline

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

lazy val root = (project in file(".")).
  settings(
    organization := "net.zhenglai",
    name := "scala-sheets",
    scalaVersion := "2.11.8",
    libraryDependencies ++= Seq(
      catsAll,
      shapelessAll,
      "org.scalaz" %% "scalaz-core" % scalazVersion
    ) ++ refined,
    scalacOptions ++= Seq(
      "-deprecation",
      "-encoding", "UTF-8",
      "-feature",
      "-language:_"
    )
  )
