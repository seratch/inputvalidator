package controllers

import _root_.play.api.mvc.MultipartFormData.FilePart
import play.api.mvc._
import inputvalidator.play.Implicits._

import inputvalidator._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index(None))
  }

  def loginInput = Action {
    Ok(views.html.loginInput(None, None, Nil))
  }

  def loginSubmit = Action {
    implicit request =>

      Validator(request.parameters)(

        inputKey("username") is required & minLength(3),
        inputKey("password") is required & minLength(5)

      ).failure { (inputs, errors) =>

          BadRequest(views.html.loginInput(
            username = inputs.get("username").map(_.toString),
            password = inputs.get("password").map(_.toString),
            errors = inputs.keys.flatMap { key =>
              errors.get(key).map { error =>
                Messages.get(
                  key = error.name,
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
    Ok(views.html.uploadInput(None, Nil))
  }

  object fileRequired extends Validation {

    def name = "fileRequired"

    def isValid(file: Any) = {
      if (file.isInstanceOf[FilePart[_]]) {
        val f: FilePart[_] = file.asInstanceOf[FilePart[_]]
        f.filename != null
      } else {
        false
      }
    }
  }

  def uploadSubmit = Action {
    implicit request =>

      Validator(request.parameters)(

        inputKey("name") is required & minLength(3),
        inputKey("profile_image") is fileRequired

      ).failure { (inputs, errors) =>

          // TODO inputs do not contain "profile_image"
          println(errors)

          BadRequest(views.html.uploadInput(
            name = inputs.get("name").map(_.toString),
            errors = inputs.keys.flatMap { key =>
              errors.get(key).map {
                error =>
                  Messages.get(
                    key = error.name,
                    params = key :: error.messageParams.toList
                  ).getOrElse(error.name)
              }
            }
          ))

        }.success {
          inputs =>

            Ok(views.html.index(inputs.get("name").map(_.toString)))

        }.apply()
  }

}
