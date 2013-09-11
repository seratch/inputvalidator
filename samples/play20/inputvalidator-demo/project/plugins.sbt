addSbtPlugin("com.typesafe.sbt" % "sbt-scalariform" % "1.0.0")

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

logLevel := Level.Warn

addSbtPlugin("play" % "sbt-plugin" % "2.1.1")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.7.3")

addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.1.1")

