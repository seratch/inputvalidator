package inputvalidator

import org.scalatest._
import org.scalatest.matchers._

class lengthSpec extends FlatSpec with ShouldMatchers {

  behavior of "length"

  it should "be available" in {
    val len = 3
    val validate = new length(len)
    validate.name should equal("length")

    validate(input("x" -> null)).isSuccess should equal(false)
    validate(input("x" -> "")).isSuccess should equal(false)
    validate(input("x" -> "1")).isSuccess should equal(false)
    validate(input("x" -> "12")).isSuccess should equal(false)
    validate(input("x" -> "123")).isSuccess should equal(true)
    validate(input("x" -> 123)).isSuccess should equal(true)
    validate(input("x" -> "1234")).isSuccess should equal(false)
    validate(input("x" -> 1234)).isSuccess should equal(false)
  }

}
