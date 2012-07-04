package inputvalidator

import org.scalatest._
import org.scalatest.matchers._

class lengthSpec extends FlatSpec with ShouldMatchers {

  behavior of "length"

  it should "be available" in {
    val len: Int = 0
    val instance = new length(len)
    instance should not be null
  }

}
