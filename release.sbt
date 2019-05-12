import sbtrelease.ReleasePlugin.autoImport.ReleaseTransformations._


// these steps are executed when we run:
// $ sbt "release with-defaults"

releaseProcess := {
  Seq[ReleaseStep](
    releaseStepCommand("clean"),
    releaseStepCommand("package"),
    releaseStepCommand("universal:publish"),
    publishArtifacts
  )
}