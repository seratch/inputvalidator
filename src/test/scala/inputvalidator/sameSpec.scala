package inputvalidator

import org.scalatest._
import org.scalatest.matchers._

class sameSpec extends FlatSpec with ShouldMatchers {

  behavior of "same"

  it should "be available" in {
    val validate = same

    validate(input("pair" -> (1, 1))).isSuccess should equal(true)
    validate(input("pair" -> ("a", "a"))).isSuccess should equal(true)
    validate(input("pair" -> (Option("a"), Option("a")))).isSuccess should equal(true)
    validate(input("pair" -> (None, None))).isSuccess should equal(true)

    validate(input("pair" -> ("1", 1))).isSuccess should equal(false)
    validate(input("pair" -> (("a", "b")))).isSuccess should equal(false)
    validate(input("pair" -> (Option("a"), Option("b")))).isSuccess should equal(false)
    validate(input("pair" -> (None, Option("")))).isSuccess should equal(false)
    validate(input("pair" -> (Option("123456"), Option("23456")))).isSuccess should equal(false)

  }

}
