package object inputvalidator {

  def input(kv: (String, Any)): KeyValueInput = KeyValueInput(kv._1, kv._2)

  def inputKey(name: String): KeyInput = KeyInput(name)

  def checkAll(vs: ValidationRule*): ValidationRule = {
    def merge(v1: ValidationRule, v2: ValidationRule): ValidationRule = {
      new Object with ValidationRule {

        def name: String = "combined-results"
        def isValid(value: Any): Boolean = throw new IllegalStateException

        override def apply(input: KeyValueInput): Validation = {
          v1.apply(input) match {
            case res1: ValidationSuccess => v2.apply(input)
            case res1: ValidationFailure =>
              ValidationFailure(input = input, errors = res1.errors ++ v2.apply(input).errors)
            case _ => throw new IllegalStateException
          }
        }

      }
    }
    vs.tail.foldLeft(vs.head) { case (vs, v) => merge(vs, v) }
  }

  private[inputvalidator] class InputWithIs(input: Input) {

    def is(validations: ValidationRule): NewValidation = NewValidation(input, validations)

    def are(validations: ValidationRule): NewValidation = NewValidation(input, validations)

  }

  implicit def InputToInputWithIs(input: Input): InputWithIs = new InputWithIs(input)

}

