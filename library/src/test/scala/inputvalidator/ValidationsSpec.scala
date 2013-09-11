package inputvalidator

import org.scalatest._
import org.scalatest.matchers._

class ValidationsSpec extends FlatSpec with ShouldMatchers {

  behavior of "Validations"

  it should "be available" in {
    val results = Nil
    val instance = new Validations(Map(), results)
    instance should not be null
  }

}
