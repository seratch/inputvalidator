package inputvalidator

trait ValidatorLike {

  val validations: Validations

  lazy val inputs: Inputs = InputsFromResults(validations)

  lazy val errors: Errors = Errors(validations.toSeq.filter {
    result => result.isInstanceOf[ValidationFailure]
  }.groupBy(_.input.key).map {
    case (key, failures) => (key, failures.flatMap(_.errors))
  })

  def map[A](extractor: Inputs => A): Form[A] = Form(validations, extractor.apply(inputs))

  def fold[A](errorsHandler: (Inputs, Errors) => A, inputsHandler: (Inputs) => A): A = {
    if (hasErrors) errorsHandler.apply(inputs, errors)
    else inputsHandler.apply(inputs)
  }

  def success[B](f: (Inputs) => B) = validations.success[B](f)

  def failure[B](f: (Inputs, Errors) => B) = validations.failure[B](f)

  def hasErrors: Boolean = !errors.isEmpty

  protected def extractValue(value: Any): Any = value match {
    case Some(v) => v
    case None => null
    case v => v
  }

}

