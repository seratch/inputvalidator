package object inputvalidator {

  def input(kv: (String, Any)): KeyValueInput = KeyValueInput(kv._1, kv._2)

  def inputKey(name: String): KeyInput = KeyInput(name)

  def checkAll(vs: Validation*): Validation = {
    def merge(v1: Validation, v2: Validation): Validation = {
      new Object with Validation {

        def name: String = "combined-validations"
        def isValid(value: Any): Boolean = throw new IllegalStateException

        override def apply(input: KeyValueInput): Result = {
          v1.apply(input) match {
            case res1: Success => v2.apply(input)
            case res1: Failure =>
              Failure(input = input, errors = res1.errors ++ v2.apply(input).errors)
            case _ => throw new IllegalStateException
          }
        }

      }
    }
    vs.tail.foldLeft(vs.head) { case (vs, v) => merge(vs, v) }
  }

  private[inputvalidator] class InputWithIs(input: Input) {

    def is(validations: Validation): NotYet = NotYet(input, validations)

    def are(validations: Validation): NotYet = NotYet(input, validations)

  }

  implicit def InputToInputWithIs(input: Input): InputWithIs = new InputWithIs(input)

}

