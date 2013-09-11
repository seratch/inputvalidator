package inputvalidator

case class Validations(inputMap: Map[String, Any], validations: Seq[Validation]) {

  def inputs: Inputs = InputsFromMap(inputMap)

  def errors: Errors = Errors(filterErrorsOnly())

  def isSuccess: Boolean = filterFailuresOnly().isEmpty

  def filterSuccessesOnly(): Seq[ValidationSuccess] = validations.filter(r => r.isInstanceOf[ValidationSuccess]).map(r => r.asInstanceOf[ValidationSuccess])

  def filterFailuresOnly(): Seq[ValidationFailure] = validations.filter(r => r.isInstanceOf[ValidationFailure]).map(r => r.asInstanceOf[ValidationFailure])

  def filterErrorsOnly(): Map[String, Seq[Error]] = filterFailuresOnly().groupBy(_.input.key).map {
    case (key, fs) => (key, fs.flatMap(_.errors))
  }

  def success[A](f: (Inputs) => A): SuccessesProjection[A] = {
    SuccessesProjection[A](this, ResultsProjection.defaultOnSuccess, ResultsProjection.defaultOnFailures).map(f)
  }

  def failure[A](f: (Inputs, Errors) => A): FailuresProjection[A] = {
    FailuresProjection[A](this, ResultsProjection.defaultOnSuccess, ResultsProjection.defaultOnFailures).map(f)
  }

  def toSeq(): Seq[Validation] = validations

  def toMap(): Map[String, Any] = Map(validations.map { r => (r.input.key, r.input.value) }: _*)

}

