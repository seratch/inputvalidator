package inputvalidator

trait ValidationRule extends Function[KeyValueInput, Validation] with Error {

  def isValid(value: Any): Boolean

  def apply(input: KeyValueInput): Validation = {
    if (isValid(input.value)) ValidationSuccess(input)
    else ValidationFailure(input, Seq(this))
  }

  def and(that: ValidationRule): ValidationRule = &(that)

  def &(that: ValidationRule): ValidationRule = {
    val _this = this
    new Object with ValidationRule {

      def name: String = "combined-results"
      def isValid(value: Any): Boolean = throw new IllegalStateException

      override def apply(input: KeyValueInput): Validation = {
        _this.apply(input) match {
          case _: ValidationSuccess => that.apply(input)
          case f: ValidationFailure => f
          case _ => throw new IllegalStateException
        }
      }

    }
  }

}

