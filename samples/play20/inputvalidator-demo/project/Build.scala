import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName         = "inputvalidator-demo"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "com.github.seratch" %% "inputvalidator" % "[0.2,)",
      "com.github.seratch" %% "inputvalidator-play" % "[0.2,)"
    )

    val main = play.Project(appName, appVersion, appDependencies).settings()

}
