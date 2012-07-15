package inputvalidator

import org.scalatest._
import org.scalatest.matchers._

class InputsSpec extends FlatSpec with ShouldMatchers {

  behavior of "Inputs"

  it should "be available" in {
    val results: Results = Results(Map(), Nil)
    val instance = InputsFromResults(results)
    instance should not be null
  }

}
