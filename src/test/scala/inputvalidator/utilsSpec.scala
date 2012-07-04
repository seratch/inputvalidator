package inputvalidator

import org.scalatest._
import org.scalatest.matchers._

class utilsSpec extends FlatSpec with ShouldMatchers {

  behavior of "utils"

  it should "be available" in {
    val singleton = utils
    singleton should not be null
  }

}
