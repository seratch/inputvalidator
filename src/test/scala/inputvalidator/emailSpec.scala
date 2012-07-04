package inputvalidator

import org.scalatest._
import org.scalatest.matchers._

class emailSpec extends FlatSpec with ShouldMatchers {

  behavior of "email"

  it should "be available" in {
    val validate = email
    validate.name should equal("email")

    validate(input("x" -> null)).isSuccess should equal(false)
    validate(input("x" -> "")).isSuccess should equal(false)
    validate(input("x" -> "  ")).isSuccess should equal(false)
    validate(input("x" -> -123)).isSuccess should equal(false)
    validate(input("x" -> 0)).isSuccess should equal(false)
    validate(input("x" -> 123)).isSuccess should equal(false)
    validate(input("x" -> "xxx")).isSuccess should equal(false)

    validate(input("x" -> "123-abc_DFG@gmail.com")).isSuccess should equal(true)
    validate(input("x" -> "a.b.c@gmail.com")).isSuccess should equal(true)

  }

}
