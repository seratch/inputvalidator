// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository 
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// Use the Play sbt plugin for Play projects
addSbtPlugin("play" % "sbt-plugin" % "2.0.2")

libraryDependencies += Defaults.sbtPluginExtra("com.typesafe.sbtscalariform" % "sbtscalariform" % "0.3.0", "0.11.2", "2.9.1")


