package inputvalidator

import org.scalatest._
import org.scalatest.matchers._

class maxLengthSpec extends FlatSpec with ShouldMatchers {

  behavior of "maxLength"

  it should "be available" in {
    val max: Int = 3
    val validate = new maxLength(max)
    validate.messageParams should equal(Seq("3"))

    validate(input("id", null)).isSuccess should equal(false)

    validate(input("id", "")).isSuccess should equal(true)

    validate(input("id", "a")).isSuccess should equal(true)
    validate(input("id", "ab")).isSuccess should equal(true)
    validate(input("id", "abc")).isSuccess should equal(true)
    validate(input("id", "abcd")).isSuccess should equal(false)

    validate(input("id", 1)).isSuccess should equal(true)
    validate(input("id", 12)).isSuccess should equal(true)
    validate(input("id", 123)).isSuccess should equal(true)
    validate(input("id", 1234)).isSuccess should equal(false)

    validate(input("id", 0.1)).isSuccess should equal(true)
    validate(input("id", 0.10)).isSuccess should equal(true)
    validate(input("id", 0.01)).isSuccess should equal(false)
    validate(input("id", 0.001)).isSuccess should equal(false)

    validate(input("list", Seq())).isSuccess should equal(true)
    validate(input("list", Seq(1))).isSuccess should equal(true)
    validate(input("list", Seq(1, 2))).isSuccess should equal(true)
    validate(input("list", Seq(1, 2, 3))).isSuccess should equal(true)

    validate(input("list", Seq(1, 2, 3, 4))).isSuccess should equal(false)
  }

}
