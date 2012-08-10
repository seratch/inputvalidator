# InputValidator for Play framework 2.x Scala

## Demo

https://github.com/seratch/inputvalidator/tree/master/samples/play20/inputvalidator-demo

## project/Build.scala

```scala
val appDependencies = Seq(
  "com.github.seratch" %% "inputvalidator" % "[0.2,)",
  "com.github.seratch" %% "inputvalidator-play" % "[0.2,)"
)
```

## app/controller/Application.scala

```scala
package controllers

import play.api.mvc._

import inputvalidator._
import inputvalidator.play._
import inputvalidator.play.Implicits._

object Application extends Controller {

  def loginInput = Action {
    Ok(views.html.loginInput(None, None, Nil))
  }

  def loginSubmit = Action { implicit request =>
    Validator(request.parameters)(
      inputKey("username") is required & minLength(3),
      inputKey("password") is required & minLength(5)
    ).failure { (inputs, errors) =>
      BadRequest(views.html.loginInput(
        username = inputs.string("username"),
        password = inputs.string("password"),
        errors = inputs.keys.flatMap { key =>
          errors.get(key).map { error =>
            Messages.get(key = error.name,
              params = key :: error.messageParams.toList
            ).getOrElse(error.name)
          }
        }
      ))
    }.success { inputs =>
      Ok(views.html.index(inputs.get("username").map(_.toString)))
    }.apply()
  }

  def uploadInput = Action {
    Ok(views.html.uploadInput(None, None, Nil))
  }

  def uploadSubmit = Action { implicit request =>
    Validator(request.parameters)(
      inputKey("name") is required & minLength(3),
      inputKey("profile_image") is fileRequired
    ).failure { (inputs, errors) =>
      BadRequest(views.html.uploadInput(
        name = inputs.string("name"),
        profileImage = inputs.get("profile_image"),
        errors = inputs.keys.flatMap { key =>
          errors.get(key).map { error =>
            Messages.get(key = error.name,
              params = key :: error.messageParams.toList
            ).getOrElse(error.name)
          }
        }
      ))
    }.success { inputs =>
      Ok(views.html.index(inputs.get("name").map(_.toString)))
    }.apply()
  }

}
```
