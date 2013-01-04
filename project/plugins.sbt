resolvers ++= Seq(
  "sonatype" at "http://oss.sonatype.org/content/repositories/releases/"
)

addSbtPlugin("com.github.seratch" % "testgenerator" % "[1.1,)")

addSbtPlugin("com.typesafe.sbt" % "sbt-scalariform" % "1.0.0")

