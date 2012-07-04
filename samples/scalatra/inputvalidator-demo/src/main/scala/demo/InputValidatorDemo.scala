package demo

import org.scalatra._
import scalate.ScalateSupport

import inputvalidator._

class InputValidatorDemo extends ScalatraServlet with ScalateSupport {

  before() {
    contentType = "text/html; charset=utf-8"
  }

  get("/") {
    ssp("index")
  }

  get("/login") {
    ssp("login")
  }

  post("/submit") {
    Validator(params)(
      inputKey("username") is required & minLength(3),
      inputKey("password") is required & minLength(5)
    ).success { inputs => 
      ssp("index", "username" -> inputs.string("username")) 
    }.failure { (inputs, errors) =>
      halt(status = 400, body = ssp("login",
        "username" -> inputs.get("username"),
        "password" -> inputs.get("password"),
        "errors" -> inputs.keys.flatMap { key =>
          errors.get(key).map { error =>
            Messages.get(
              key = error.name,
              params = key :: error.messageParams.toList
            ).getOrElse(error.name)
          }
        }
      ))
    }.apply()
  }

  notFound {
    // Try to render a ScalateTemplate if no route matched
    findTemplate(requestPath) map {
      path =>
        contentType = "text/html"
        layoutTemplate(path)
    } orElse serveStaticResource() getOrElse resourceNotFound()
  }
}
