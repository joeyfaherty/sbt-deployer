import sbtrelease.ReleasePlugin.autoImport.ReleaseTransformations._


// these steps are executed when we run:
// $ sbt "release with-defaults"

// enables the archive to be packaged
enablePlugins(UniversalDeployPlugin)

// these release steps are executed in sequence when the below command is run:
// sbt "release with-defaults"
releaseProcess := {
  Seq[ReleaseStep](
    releaseStepCommand("clean"),
    // creates a zip file package in target/universal
    releaseStepCommand("universal:packageBin"),
    // creates the fat jar
    releaseStepCommand("universal:assembly"),
    // publishes this zip file to artifactory
    releaseStepCommand("universal:publish"),
    // generic publish which publishes the root app jar, javadoc, sources and pom.xml
    //publishArtifacts
  )
}