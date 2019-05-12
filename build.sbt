import sbt.Keys._
import Dependencies._

name := "sbt-deploy"

version := "0.1"

scalaVersion := "2.12.8"

enablePlugins(JavaAppPackaging)

import NativePackagerKeys._
import com.typesafe.sbt.SbtNativePackager.packageArchetype

publishTo := Some(Resolver.file("file", new File("/tmp/my/artifactory")))

name := "sbt-magic-joey"

// used like the groupId in maven
organization in ThisBuild := "my.home"

import com.typesafe.sbt.SbtNativePackager.Universal

/*mappings in Universal += {
  file("tmp/xxx.txt") -> "conf/my.txt"
}*/

/*SETTINGS*/
lazy val commonSettings = Seq(libraryDependencies ++= commonDependencies)



val cqlDdlFile = taskKey[File]("cql-ddl")
cqlDdlFile := {
  (baseDirectory in Compile).value / "config" / "cql" / "myDDL.cql"
}

val packageWar = taskKey[File]("package-war")
packageWar := {
  val warFile = name.value + "-" + version.value + ".jar"
  (baseDirectory in Compile).value / apiApp.id / "target" / warFile
}

// create an Artifact for publishing the .war file
artifact in (Compile, packageWar) := {
  val previous: Artifact = (artifact in (Compile, packageWar)).value
  previous
    .withType("war")
    .withExtension("war")
    .withClassifier(Some("pretend-war-file"))
}

artifact in (Compile, cqlDdlFile) := {
  val previous: Artifact = (artifact in (Compile, cqlDdlFile)).value
  previous
    .withType("cql")
    .withExtension("cql")
    .withClassifier(Some("ddl scripts"))
}

// add the .war file to what gets published
addArtifact(artifact in (Compile, packageWar), packageWar)
// add the .cql file to what gets published
addArtifact(artifact in (Compile, cqlDdlFile), cqlDdlFile)


// this is the root project, aggregating all sub projects

lazy val root = Project(
  id = "root",
  base = file("."),
) dependsOn(apiApp, service, common) // this does the actual aggregation

// --------- Project Api ------------------
lazy val apiApp = Project(
  id = "api-app",
  base = file("api-app")
)
  .settings(commonSettings: _*)
  .dependsOn (common)
  //.settings(publishArtifact in(Compile, packageBin) := false)


// --------- Project Service ----------------
lazy val service = Project(
  id = "service",
  base = file("service")
).dependsOn (common)
  //.settings(publishArtifact in(Compile, packageBin) := false)
  //.settings(publishLocal := true)


// --------- Project Common ------------------
lazy val common = Project(
  id = "common",
  base = file("common")
)