package inputvalidator

import org.scalatest._
import org.scalatest.matchers._

class intMinValueSpec extends FlatSpec with ShouldMatchers {

  behavior of "intMinValue"

  it should "be available" in {
    val min: Int = 2
    val validate = new intMinValue(min)
    validate.name should equal("intMinValue")

    validate.messageParams should equal(Seq("2"))

    validate(KeyValueInput("id", -1)).isSuccess should equal(false)
    validate(KeyValueInput("id", 0)).isSuccess should equal(false)
    validate(KeyValueInput("id", 1)).isSuccess should equal(false)
    validate(KeyValueInput("id", 2)).isSuccess should equal(true)
    validate(KeyValueInput("id", 3)).isSuccess should equal(true)
    validate(KeyValueInput("id", 4)).isSuccess should equal(true)
    validate(KeyValueInput("id", 5)).isSuccess should equal(true)
    validate(KeyValueInput("id", 6)).isSuccess should equal(true)
    validate(KeyValueInput("id", 7)).isSuccess should equal(true)
  }

}
