resolvers ++= Seq(
  Classpaths.typesafeResolver,
  "sonatype" at "http://oss.sonatype.org/content/repositories/releases/"
)

addSbtPlugin("com.typesafe.sbt" % "sbt-scalariform" % "[1.0,)")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.5.1")


