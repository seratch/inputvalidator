# InputValidator

## Important Notice

inputvalidator is already in maintenance mode. So only bug fixes will be released and new features won't be implemented.

Now I'm working harder on skinny-validator (it's a successor of inputvalidator and some APIs are refactored). skinny-validator is a part of skinny-framework but it doesn't depend on skinny. You can use skinny-validator anywhere as same as well as inputvalidator.

https://github.com/skinny-framework/skinny-framework/tree/develop/validator

http://skinny-framework.org/documentation/validator.html

## Scala Input Validator with quite readable DSL

```
libraryDependencies += "com.github.seratch" %% "inputvalidator" % "[1.0,)"
```

Use as follows:

```scala
import inputvalidator._

case class Registration(id: Int, firstName: String, lastName: Option[String], gender: String)

val validator = Validator {
  input("id" -> 12345) is required & maxLength(4), // 1 error
  input("first_name" -> "") is notNull & minLength(3), // 1 error
  input("last_name" -> "Kaz") is checkAll(minLength(5), numeric), // 2 errors
  input("gender" -> "male") is notEmpty

}

val res: String = validator.success {
  inputs => inputs.string("first_name")
}.failure {
  (inputs, errors) => "Failed! : " + errors
}.apply()

val form = validator.map { inputs => 
  Registration(
    id        = inputs.int("id"),
    firstName = inputs.string("first_name"),
    lastName  = inputs.stringOpt("last_name"),
    gender    = inputs.string("gender")
  )
}

if (form.hasErrors) {
  BadRequest(form.errors)
} else {
  val registration = form.get
  User.create(registration)
  Ok
}
```

Accepting `Map` value is also simple:

```scala
val params: Map[String, Any] = request.getParameterMap()

val v = Validator(params)(
  inputKey("password") is required,
  inputKey("newPassword") is (required and minLength(6)),
  inputKey("reNewPassword") is (required and minLength(6)),
  input("pair" -> (params.get("newPassword"), params.get("reNewPassword"))) are same
)
```

## Built-in Validations

See the following source code:

https://github.com/seratch/inputvalidator/blob/master/library/src/main/scala/inputvalidator/BuiltinValidations.scala

Your pull requests are always welcome.


## Easily Extendable

If you need to add validations, it's quite simple.

Just add new class which extends `inputvalidator.ValidationRule` as follows:

```scala
object authenticated extends ValidationRule {
  def name = "authenticated"
  def isValid(v: Any) = {
    val (username, password) = v.asInstanceOf[(String, String)]
    UserService.authenticate(username, password).isDefined
  }
}

val v = Valdiator(
  input("username" -> username) is required,
  input("passowrd" -> password) is required,
  input("login" -> (username, password)) is authenticated
)
```


## Message Binding

Supports the standard way to use `messages.properties`.

```scala
post("login") {
  Validator(params)(
    input("username" -> username) is required,
    input("passowrd" -> password) is required,
    input("login" -> (username, password)) is authenticated
  ).success { inputs =>
    respond(status = 200, body = render("completed.ssp"))
  }.failure { (inputs, errors) =>
    respond(
      status = 400, 
      body = render("input.ssp",
        "useraname" -> inputs.get("username"),
        "password" -> inputs.get("password"),
        "errors" -> inputs.keys.flatMap { key =>
          errors.get(key).map { error =>
            Messages.get(key = error.name, params = key :: error.messageParams.toList).getOrElse(error.name)
          }
        }
      )
    )
  }.apply()
}
```


## Do the Same Everywhere

Furthermore, this library does not depend on any other components. So you can do the same everywhere.

Please see the following examples.

- Play framework 2.x Scala

https://github.com/seratch/inputvalidator/tree/master/play-module

https://github.com/seratch/inputvalidator/tree/master/samples/play20

- Scalatra

https://github.com/seratch/inputvalidator/tree/master/samples/scalatra

- Circumflex

https://github.com/seratch/inputvalidator/tree/master/samples/circumflex


## License

Copyright 2013 Kazuhiro Sera

Apache License, Version 2.0

http://www.apache.org/licenses/LICENSE-2.0.html

