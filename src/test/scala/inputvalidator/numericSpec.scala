package inputvalidator

import org.scalatest._
import org.scalatest.matchers._

class numericSpec extends FlatSpec with ShouldMatchers {

  behavior of "numeric"

  it should "be available" in {
    val validate = numeric
    validate.name should equal("numeric")

    validate(input("id", "abc")).isSuccess should equal(false)
    validate(input("id", "あ")).isSuccess should equal(false)
    validate(input("id", "1a")).isSuccess should equal(false)

    validate(input("id", null)).isSuccess should equal(false)
    validate(input("id", "")).isSuccess should equal(false)

    validate(input("id", "0")).isSuccess should equal(true)
    validate(input("id", 0)).isSuccess should equal(true)

    validate(input("id", -1)).isSuccess should equal(true)
    validate(input("id", -0.1D)).isSuccess should equal(true)

    validate(input("id", 1)).isSuccess should equal(true)
    validate(input("id", 2)).isSuccess should equal(true)
    validate(input("id", 3)).isSuccess should equal(true)
    validate(input("id", 0.3D)).isSuccess should equal(true)
    validate(input("id", 0.3F)).isSuccess should equal(true)
    validate(input("id", 123L)).isSuccess should equal(true)

  }

}
