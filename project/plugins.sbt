externalResolvers ~= (_.filter(_.name != "Scala-Tools Maven2 Repository"))

resolvers ++= Seq(
  Classpaths.typesafeResolver,
  "sonatype" at "http://oss.sonatype.org/content/repositories/releases/",
  "idea" at "http://mpeltonen.github.com/maven/",
  "less" at "http://repo.lessis.me"
)

addSbtPlugin("com.github.seratch" %% "testgenerator" % "1.1.0")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.0.0")

addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.0.0")

//addSbtPlugin("com.typesafe.sbtscalariform" % "sbtscalariform" % "0.3.0")
libraryDependencies += Defaults.sbtPluginExtra("com.typesafe.sbtscalariform" % "sbtscalariform" % "0.3.0", "0.11.2", "2.9.1")

//addSbtPlugin("me.lessis" % "ls-sbt" % "0.1.1")
libraryDependencies += Defaults.sbtPluginExtra("me.lessis" % "ls-sbt" % "0.1.1", "0.11.2", "2.9.1")

// for sonatype publishment

resolvers += Resolver.url("sbt-plugin-releases", new URL("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases/"))(Resolver.ivyStylePatterns)

//addSbtPlugin("com.jsuereth" % "xsbt-gpg-plugin" % "0.5")
libraryDependencies += Defaults.sbtPluginExtra("com.jsuereth" % "xsbt-gpg-plugin" % "0.5", "0.11.2", "2.9.1")


