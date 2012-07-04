package inputvalidator

import org.scalatest._
import org.scalatest.matchers._

class longMinMaxValueSpec extends FlatSpec with ShouldMatchers {

  behavior of "longMinMaxValue"

  it should "be available" in {
    val min = 2L
    val max = 5L
    val validate = new longMinMaxValue(min, max)
    validate.messageParams should equal(Seq("2", "5"))

    validate(input("id", -1)).isSuccess should equal(false)
    validate(input("id", 0)).isSuccess should equal(false)
    validate(input("id", 1)).isSuccess should equal(false)
    validate(input("id", 2)).isSuccess should equal(true)
    validate(input("id", 3)).isSuccess should equal(true)
    validate(input("id", 4)).isSuccess should equal(true)
    validate(input("id", 5)).isSuccess should equal(true)
    validate(input("id", 6)).isSuccess should equal(false)
    validate(input("id", 7)).isSuccess should equal(false)
  }

}
