# SBT - Build, Assemble, Publish.

0. All dependencies kept in one place in the project/Dependencies.scala a tidy build.sbt and to ensure D.R.Y.
1. builds all the modules, creating a jar for each in their target folders.
2. builds a fat jar (containing main jar + all external deps)
3. create a tar.gz distribution file that has the fat jar inside + cql ddl files + a runner.sh file
4. set up the release process so that this tar.gz distribution artifact is published to remote repo


To test, this command:
```$ sbt clean "release with-defaults"```

should produce

./target/*.tar.gz

and in the remote repo it should be published:
- tar file
  - war file
  - ddl file
  - runner.sh
  
  


