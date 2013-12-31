resolvers ++= Seq(
  Classpaths.typesafeResolver,
  "sonatype" at "http://oss.sonatype.org/content/repositories/releases/"
)

addSbtPlugin("com.typesafe.sbt" % "sbt-scalariform" % "1.2.1")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.7.4")

addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.1.2")

