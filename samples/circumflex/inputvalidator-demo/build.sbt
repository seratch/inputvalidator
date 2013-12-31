seq(webSettings :_*)

organization := "com.github.seratch"

name := "inputvalidator-circumflex-demo"

scalaVersion := "2.9.1"

version := "0.1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "com.github.seratch" %% "inputvalidator" % "1.0.0",
  "ru.circumflex" % "circumflex-web" % "2.5" % "compile",
  "ru.circumflex" % "circumflex-core" % "2.5" % "compile",
  "org.fusesource.scalate" % "scalate-core" % "1.5.3" % "compile",
  "org.mortbay.jetty" % "jetty" % "6.1.26" % "container"
)

resolvers ++= Nil
