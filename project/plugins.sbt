resolvers ++= Seq(
  "sonatype" at "http://oss.sonatype.org/content/repositories/releases/"
)

addSbtPlugin("com.github.seratch" % "testgenerator" % "[1.1,)")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "[1.1,)")

addSbtPlugin("com.typesafe.sbtscalariform" % "sbtscalariform" % "0.5.1")

// for sonatype publishment

resolvers += Resolver.url("sbt-plugin-releases for Travis CI", new URL("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases/"))(Resolver.ivyStylePatterns)

addSbtPlugin("com.jsuereth" % "xsbt-gpg-plugin" % "[0.6,)")

