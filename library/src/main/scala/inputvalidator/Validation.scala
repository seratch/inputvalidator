package inputvalidator

trait Validation extends Function[KeyValueInput, Result] with Error {

  def isValid(value: Any): Boolean

  def apply(input: KeyValueInput): Result = {
    if (isValid(input.value)) Success(input)
    else Failure(input, Seq(this))
  }

  def and(that: Validation): Validation = &(that)

  def &(that: Validation): Validation = {
    val _this = this
    new Object with Validation {

      def name: String = "combined-results"
      def isValid(value: Any): Boolean = throw new IllegalStateException

      override def apply(input: KeyValueInput): Result = {
        _this.apply(input) match {
          case _: Success => that.apply(input)
          case f: Failure => f
          case _ => throw new IllegalStateException
        }
      }

    }
  }

}

