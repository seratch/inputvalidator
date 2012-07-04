package controllers

import play.api._
import play.api.mvc._

import inputvalidator._

object Application extends Controller {

  // TODO
  case class RequestWithParameters(req: Request[_]) { 
    def parameters: Map[String, String] = {
      ((req.body match {
        case body: AnyContent if body.asFormUrlEncoded.isDefined => body.asFormUrlEncoded.get
        case body: AnyContent if body.asMultipartFormData.isDefined => body.asMultipartFormData.get.asFormUrlEncoded
        case body: Map[_, _] => body.asInstanceOf[Map[String, Seq[String]]]
        case body: MultipartFormData[_] => body.asFormUrlEncoded
        case _ => Map.empty[String, Seq[String]]
      }) ++ req.queryString).map { case (k,v) => (k, v.mkString) }
    }
  }
  implicit def ToRequestWithParameters(req: Request[_]) = RequestWithParameters(req)

  def index = Action {
    Ok(views.html.index(None))
  }

  def loginInput = Action { 
    Ok(views.html.loginInput(None, None, Nil))
  }

  def loginSubmit = Action { implicit request =>
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
  
}
