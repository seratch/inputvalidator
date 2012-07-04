package inputvalidator

import org.scalatest._
import org.scalatest.matchers._

import org.joda.time._

class pastSpec extends FlatSpec with ShouldMatchers {

  behavior of "past"

  it should "be available" in {
    val validate = past
    validate(input("x" -> null)).isSuccess should equal(false)

    validate(input("x" -> DateTime.now.minusDays(1))).isSuccess should equal(true)

    val judPast = DateTime.now.minusDays(1).toDate
    validate(input("x" -> judPast)).isSuccess should equal(true)

    validate(input("x" -> DateTime.now.plusDays(1))).isSuccess should equal(false)

    val judFuture = DateTime.now.plusDays(1).toDate
    validate(input("x" -> judFuture)).isSuccess should equal(false)
  }

}
