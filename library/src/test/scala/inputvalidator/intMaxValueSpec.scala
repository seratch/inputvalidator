package inputvalidator

import org.scalatest._
import org.scalatest.matchers._

class intMaxValueSpec extends FlatSpec with ShouldMatchers {

  behavior of "intMaxValue"

  it should "be available" in {
    val max = 3
    val validate = new intMaxValue(max)
    validate.name should equal("intMaxValue")

    validate.messageParams should equal(Seq("3"))

    validate(input("id", -1)).isSuccess should equal(true)
    validate(input("id", 0)).isSuccess should equal(true)
    validate(input("id", 1)).isSuccess should equal(true)
    validate(input("id", 2)).isSuccess should equal(true)
    validate(input("id", 3)).isSuccess should equal(true)
    validate(input("id", 4)).isSuccess should equal(false)
  }

}
