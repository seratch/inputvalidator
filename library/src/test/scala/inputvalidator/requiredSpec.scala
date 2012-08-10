package inputvalidator

import org.scalatest._
import org.scalatest.matchers._

class requiredSpec extends FlatSpec with ShouldMatchers {

  behavior of "required"

  it should "be available" in {
    val validate = required
    validate.name should equal("required")

    validate(input("id", null)).isSuccess should equal(false)
    validate(input("id", "")).isSuccess should equal(false)
    validate(input("id", "  ")).isSuccess should equal(false)

    validate(input("id", "   ")).isSuccess should equal(false)

    validate(input("id", -1)).isSuccess should equal(true)
    validate(input("id", 0)).isSuccess should equal(true)
    validate(input("id", 1)).isSuccess should equal(true)
    validate(input("id", 2)).isSuccess should equal(true)
  }

}
