package com.github.seratch

import ru.circumflex._
import ru.circumflex.core._
import ru.circumflex.web._

import inputvalidator._

class MyRouter extends Router with ScalateSupport {

  get("/") = render("WEB-INF/views/index.ssp", Map())

  get("/login") = render("WEB-INF/views/login.ssp", Map())

  post("/submit") = {
    Validator(request.params.toMap)(
      inputKey("username") is required & minLength(3),
      inputKey("password") is required & minLength(5)
    ).failure { (inputs, errors) =>
      render("WEB-INF/views/login.ssp", 
        Map(
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
        )
      )
    }.success { inputs =>
      render("WEB-INF/views/index.ssp", 
        Map("username" -> inputs.get("username").map(_.toString))
      )
    }.apply()
  }

}


