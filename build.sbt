import Dependencies._

ThisBuild / organization := "com.esteban"
ThisBuild / scalaVersion := "3.1.0"
javacOptions ++= Seq("-source", "11", "-target", "11")
scalacOptions --= Seq(
  "-target:jvm-11",
  "-Xfatal-warnings",
  "-Wunused",
  "-language:postfixOps",
  "-Yexplicit-nulls",
  "-source",
  "3.1",
)
initialize := {
  val _ = initialize.value
  val javaVersion = sys.props("java.specification.version")
  if (javaVersion != "11")
    sys.error(
      "Java 11 is required for this project. Found " + javaVersion + " instead"
    )
}

ThisBuild / scalacOptions ++=
  Seq(
    "-deprecation",
    "-feature",
    "-language:implicitConversions",
    "-unchecked",
    "-Xfatal-warnings",
    "-Yexplicit-nulls", // experimental (I've seen it cause issues with circe)
    "-Ykind-projector",
    "-Ysafe-init", // experimental (I've seen it cause issues with circe)
  ) ++ Seq("-rewrite", "-indent") ++ Seq("-source", "future")

lazy val `scalaalgo` =
  project
    .in(file("."))
    .settings(name := "scalaAlgo")
    .settings(commonSettings)
    .settings(dependencies)

lazy val commonSettings = commonScalacOptions ++ Seq(
  update / evictionWarningOptions := EvictionWarningOptions.empty
)

lazy val commonScalacOptions = Seq(
  Compile / console / scalacOptions --= Seq(
    "-Wunused:_",
    "-Xfatal-warnings",
  ),
  Test / console / scalacOptions :=
    (Compile / console / scalacOptions).value,
)

lazy val dependencies = Seq(
  libraryDependencies ++= Seq(
    // main dependencies
    org.cats.catsEffect,
    org.cats.cats
  ),
  libraryDependencies ++= Seq(
    org.scalatest.scalatest,
    org.scalatestplus.`scalacheck-1-15`,
  ).map(_ % Test),
)
