package inputvalidator

import org.scalatest._
import org.scalatest.matchers._

class ResultsSpec extends FlatSpec with ShouldMatchers {

  behavior of "Results"

  it should "be available" in {
    val results = Nil
    val instance = new Results(results)
    instance should not be null
  }

}
