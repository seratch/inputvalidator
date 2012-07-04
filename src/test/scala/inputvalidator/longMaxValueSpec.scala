package inputvalidator

import org.scalatest._
import org.scalatest.matchers._

class longMaxValueSpec extends FlatSpec with ShouldMatchers {

  behavior of "longMaxValue"

  it should "be available" in {
    val max = 3L
    val validate = new longMaxValue(max)
    validate.name should equal("longMaxValue")
    validate.messageParams should equal(Seq("3"))

    validate(input("id", -1)).isSuccess should equal(true)
    validate(input("id", 0)).isSuccess should equal(true)
    validate(input("id", 1)).isSuccess should equal(true)
    validate(input("id", 2)).isSuccess should equal(true)
    validate(input("id", 3)).isSuccess should equal(true)
    validate(input("id", 4)).isSuccess should equal(false)
  }

}
