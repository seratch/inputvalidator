import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "inputvalidator-demo"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "com.github.seratch" %% "inputvalidator" % "[0.1,)"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
    )

}
