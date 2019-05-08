name := "sbt-deploy"

version := "0.1"

scalaVersion := "2.12.8"

enablePlugins(JavaAppPackaging)

import NativePackagerKeys._
import com.typesafe.sbt.SbtNativePackager.packageArchetype

name := "sbt-magic-joey"

// used like the groupId in maven
organization in ThisBuild := "my.home"

import com.typesafe.sbt.SbtNativePackager.Universal

mappings in Universal += {
  file("tmp/xxx.txt") -> "conf/my.txt"
}

// this is the root project, aggregating all sub projects
lazy val root = Project(
  id = "root",
  base = file("."),
) dependsOn(apiApp, service, common) // this does the actual aggregation

// --------- Project Api ------------------
lazy val apiApp = Project(
  id = "api-app",
  base = file("api-app")
) dependsOn(common)


// --------- Project Service ----------------
lazy val service = Project(
  id = "service",
  base = file("service")
) dependsOn(common)

// --------- Project Common ------------------
lazy val common = Project(
  id = "common",
  base = file("common")
)