package inputvalidator

import org.scalatest._
import org.scalatest.matchers._
import java.util.Properties
import java.util.Locale

class MessagesSpec extends FlatSpec with ShouldMatchers {

  behavior of "Messages"

  it should "be available" in {
    Messages.get("foo").get should equal("bar")
    Messages.get("fooo").isDefined should equal(false)
    Messages.get("intMinValue", Seq("age", 20)).get should equal(
      "age should be greater than or equal to 20.")
    val messages = Validator(
      input("age" -> 12) is intMinValue(20)
    ).failure { (inputs, errors) =>
        inputs.keys.flatMap { key =>
          errors.get(key).map { error =>
            val msg = Messages.get(error.name, key :: error.messageParams.toList).get
            msg should equal("age should be greater than or equal to 20.")
            msg
          }
        }
      }.apply()
    messages.size should equal(1)
  }

}
