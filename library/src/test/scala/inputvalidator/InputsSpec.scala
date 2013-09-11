package inputvalidator

import org.scalatest._
import org.scalatest.matchers._

class InputsSpec extends FlatSpec with ShouldMatchers {

  behavior of "Inputs"

  it should "be available" in {
    val results: Validations = Validations(Map(), Nil)
    val instance = InputsFromResults(results)
    instance should not be null
  }

  it should "return value with values" in {
    val map: Map[String, Any] = Map(
      "name" -> "Alice",
      "age" -> 19,
      "active" -> true,
      "average_point" -> 0.12D
    )
    val inputs = InputsFromMap(map)
    inputs.string("name") should equal("Alice")
    inputs.int("age") should equal(19)
    inputs.short("age") should equal(19)
    inputs.byte("age") should equal(19)
    inputs.boolean("active") should equal(true)
    inputs.double("average_point") should equal(0.12D)
    inputs.float("average_point") should equal(0.12F)
  }

  it should "return value as convertable value" in {
    val map: Map[String, Any] = Map(
      "name" -> "Alice",
      "age" -> "19",
      "active" -> "true",
      "average_point" -> "0.12"
    )
    val inputs = InputsFromMap(map)
    inputs.int("age") should equal(19)
    inputs.short("age") should equal(19)
    inputs.long("age") should equal(19)
    inputs.byte("age") should equal(19)
    inputs.boolean("active") should equal(true)
    inputs.double("average_point") should equal(0.12D)
    inputs.float("average_point") should equal(0.12F)
  }

}
