package inputvalidator

import org.scalatest._
import org.scalatest.matchers._

class longMinValueSpec extends FlatSpec with ShouldMatchers {

  behavior of "longMinValue"

  it should "be available" in {
    val min = 2L
    val validate = new longMinValue(min)
    validate.name should equal("longMinValue")
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
