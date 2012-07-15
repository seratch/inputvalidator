package inputvalidator

import org.scalatest._
import org.scalatest.matchers._

class ValidatorSpec extends FlatSpec with ShouldMatchers {

  behavior of "Validator"

  it should "pass all the valid values" in {
    val validator = Validator(
      input("id" -> 12345) is notNull,
      input("first_name" -> "Kaz") is required & minLength(3),
      input("last_name" -> "Sera") is checkAll(minLength(1), maxLength(5))
    )

    val res1: Int = validator.success {
      inputs => inputs.int("id").getOrElse(-1)
    }.failure {
      (inputs, errors) => -1
    }.apply()
    res1 should equal(12345)

    val res2: Int = validator.fold(
      (inputs: Inputs, errors: Errors) => -1,
      (inputs: Inputs) => inputs.int("id").getOrElse(-1)
    )
    res2 should equal(12345)

    validator.errors.size should equal(0)
  }

  it should "find invalid values" in {
    val validator = Validator(
      input("id" -> 12345) is notNull & maxLength(4), // 1 error
      input("first_name" -> "") is required & minLength(3), // 1 error
      input("last_name" -> "Sera") is checkAll(required, minLength(5), numeric), // 2 errors
      input("gender" -> "male") is required
    )

    val res: String = validator.success {
      inputs => "Success!"
    }.failure {
      (inputs, errors) => "Failed!"
    }.apply()
    res should equal("Failed!")

    validator.errors.size should equal(3)
    validator.errors.toMap.toSeq.flatMap(_._2).size should equal(4)
  }

  it should "define and alias" in {
    val validator = Validator(
      input("id" -> 12345) is (notNull and maxLength(4)), // 1 error
      input("first_name" -> "") is (required and minLength(3)) // 1 error
    )

    val res: String = validator.success {
      inputs => "Success!"
    }.failure {
      (inputs, errors) => "Failed!"
    }.apply()
    res should equal("Failed!")

    validator.errors.size should equal(2)
  }

  it should "be able to use #checkAll" in {
    val v = Validator(
      input("id" -> "") is (checkAll(required, numeric, minLength(3)))
    )
    v.errors.get("id").size should equal(3)
  }

  it should "accept Option input values" in {
    val params = Map("id" -> 12345, "name" -> "Sera")
    val v = Validator(
      input("id" -> params.get("id")) is notNull & maxLength(4),
      input("name" -> params.get("name")) is required & minLength(5),
      input("gender" -> params.get("gender")) is required
    )
    v.errors.size should equal(3)
  }

  it should "accept Map value" in {
    val params = Map("password" -> "", "newPassword" -> "123456", "reNewPassword" -> "2345")
    val v = Validator(params)(
      inputKey("password") is required,
      inputKey("newPassword") is required & minLength(6),
      inputKey("reNewPassword") is required & minLength(6),
      input("pair" -> (params.get("newPassword"), params.get("reNewPassword"))) are same
    )
    v.errors.size should equal(3)
  }

  object AuthService {
    def authenticate(username: String, password: String) = username == password
  }
  case object authenticated extends Validation {
    def name = "authenticated"
    def isValid(v: Any) = {
      val (username, password) = v.asInstanceOf[(String, String)]
      AuthService.authenticate(username, password)
    }
  }

  it should "perform authentication failure" in {
    Validator()(
      input("login" -> ("id", "pass")) is authenticated
    ).failure { (inputs, errors) =>
        errors.get("login").head.name == "authenticated"
      }.success { inputs => fail() }.apply()
  }

  it should "perform authentication success" in {
    val results = Validator()(
      input("login" -> ("idpass", "idpass")) is authenticated
    ).success { inputs => }.failure { (inputs, errors) => fail() }.apply()
  }

  it should "apply several times (success)" in {
    Validator
      .apply(input("a", "aa") is required)
      .apply(input("b", "bb") is required)
      .success { inputs =>
        inputs.string("a").get should equal("aa")
        inputs.string("b").get should equal("bb")
      }.failure { (inputs, errors) =>
        fail()
      }.apply()

    Validator(Map("a" -> 1, "b" -> 2, "c" -> 3))
      .apply(inputKey("a") is required)
      .apply(inputKey("b") is required)
      .success { inputs =>
        inputs.int("a").get should equal(1)
        inputs.int("b").get should equal(2)
        inputs.int("c").get should equal(3)
      }.failure { (inputs, errors) =>
        fail()
      }.apply()
  }

  it should "apply several times (failure)" in {
    Validator
      .apply(input("a", null) is required)
      .apply(input("b", "") is required)
      .apply(input("c", "cc") is required)
      .success { inputs =>
        fail()
      }.failure { (inputs, errors) =>
        inputs.string("a").get should equal(null)
        inputs.string("b").get should equal("")
        inputs.string("c").get should equal("cc")
      }.apply()

    Validator(Map("a" -> null, "b" -> "", "c" -> "cc"))
      .apply(inputKey("a") is required)
      .apply(inputKey("b") is required)
      .success { inputs =>
        fail()
      }.failure { (inputs, errors) =>
        inputs.string("a").get should equal(null)
        inputs.string("b").get should equal("")
        inputs.string("c").get should equal("cc")
      }.apply()
  }
}
