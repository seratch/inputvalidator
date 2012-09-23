organization := "com.github.seratch"

name := "inputvalidator-demo"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.9.1"

seq(webSettings :_*)

libraryDependencies ++= Seq(
  "com.github.seratch" %% "inputvalidator" % "[0.2,)",
  "org.scalatra" %% "scalatra" % "2.0.4",
  "org.scalatra" %% "scalatra-scalate" % "2.0.4",
  "org.scalatra" %% "scalatra-specs2" % "2.0.4" % "test",
  "ch.qos.logback" % "logback-classic" % "1.0.0" % "runtime",
  "org.eclipse.jetty" % "jetty-webapp" % "7.6.0.v20120127" % "container",
  "javax.servlet" % "servlet-api" % "2.5" % "provided"
)

resolvers += "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"
