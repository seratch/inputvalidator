package inputvalidator

import org.scalatest._
import org.scalatest.matchers._

class notNullSpec extends FlatSpec with ShouldMatchers {

  behavior of "notNull"

  it should "be available" in {
    val validate = notNull
    validate.name should equal("notNull")

    validate(input("id", null)).isSuccess should equal(false)

    validate(input("id", "")).isSuccess should equal(true)
    validate(input("id", "  ")).isSuccess should equal(true)

    validate(input("id", "   ")).isSuccess should equal(true)

    validate(input("id", -1)).isSuccess should equal(true)
    validate(input("id", 0)).isSuccess should equal(true)
    validate(input("id", 1)).isSuccess should equal(true)
    validate(input("id", 2)).isSuccess should equal(true)
  }

}
