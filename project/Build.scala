import sbt._
import Keys._

object AppBuild extends Build {

  // library project

  val _version = "0.2.1"

  lazy val libraryProject = Project(id = "library", base = file("library"), settings = Defaults.defaultSettings ++ Seq(
    sbtPlugin := false,
    organization := "com.github.seratch",
    name := "inputvalidator",
    version := _version,
    scalaVersion := "2.9.2",
    crossScalaVersions := Seq("2.9.2", "2.9.1"),
    externalResolvers ~= (_.filter(_.name != "Scala-Tools Maven2 Repository")),
    resolvers ++= Seq(
      "typesafe releases" at "http://repo.typesafe.com/typesafe/releases",
      "sonatype releases" at "http://oss.sonatype.org/content/repositories/releases/"
    ),
    libraryDependencies <++= (scalaVersion) {
      scalaVersion =>
        Seq(
          "joda-time" % "joda-time" % "2.1" % "test",
          "org.joda" % "joda-convert" % "1.2" % "test",
          "org.scalatest" %% "scalatest" % "1.7.2" % "test"
        )
    },
    publishTo <<= version {
      (v: String) =>
        val nexus = "https://oss.sonatype.org/"
        if (v.trim.endsWith("SNAPSHOT")) Some("snapshots" at nexus + "content/repositories/snapshots")
        else Some("releases" at nexus + "service/local/staging/deploy/maven2")
    },
    publishMavenStyle := true,
    publishArtifact in Test := false,
    pomIncludeRepository := {
      x => false
    },
    pomExtra := _pomExtra,
    scalacOptions ++= Seq("-deprecation", "-unchecked"))
  )

  lazy val playModuleProject = Project(id = "play-module", base = file("play-module"), settings = Defaults.defaultSettings ++ Seq(
    sbtPlugin := false,
    organization := "com.github.seratch",
    name := "inputvalidator-play",
    version := _version,
    scalaVersion := "2.9.2",
    crossScalaVersions := Seq("2.9.2", "2.9.1"),
    externalResolvers ~= (_.filter(_.name != "Scala-Tools Maven2 Repository")),
    resolvers ++= Seq(
      "typesafe releases" at "http://repo.typesafe.com/typesafe/releases",
      "sonatype releases" at "http://oss.sonatype.org/content/repositories/releases/"
    ),
    libraryDependencies <++= (scalaVersion) {
      scalaVersion =>
        Seq(
          "com.github.seratch" %% "inputvalidator" % _version,
          "play" % "play_2.9.1" % "2.0.3" % "provided",
          "play" % "play-test_2.9.1" % "2.0.3" % "test",
          "org.joda" % "joda-convert" % "1.2" % "test",
          "org.scalatest" %% "scalatest" % "1.7.2" % "test"
        )
    },
    publishTo <<= version {
      (v: String) =>
        val nexus = "https://oss.sonatype.org/"
        if (v.trim.endsWith("SNAPSHOT")) Some("snapshots" at nexus + "content/repositories/snapshots")
        else Some("releases" at nexus + "service/local/staging/deploy/maven2")
    },
    publishMavenStyle := true,
    publishArtifact in Test := false,
    pomIncludeRepository := {
      x => false
    },
    pomExtra := _pomExtra,
    scalacOptions ++= Seq("-deprecation", "-unchecked"))
  )

  val _pomExtra = (
    <url>https://github.com/seratch/inputvalidator</url>
      <licenses>
        <license>
          <name>Apache License, Version 2.0</name>
          <url>http://www.apache.org/licenses/LICENSE-2.0.html</url>
          <distribution>repo</distribution>
        </license>
      </licenses>
      <scm>
        <url>git@github.com:seratch/inputvalidator.git</url>
        <connection>scm:git:git@github.com:seratch/inputvalidator.git</connection>
      </scm>
      <developers>
        <developer>
          <id>seratch</id>
          <name>Kazuhiro Sera</name>
          <url>https://github.com/seratch</url>
        </developer>
      </developers>
    )

}


