package inputvalidator

trait ValidatorLike {

  val results: Results

  lazy val inputs: Inputs = InputsFromResults(results)

  lazy val errors: Errors = Errors(results.toSeq.filter {
    result => result.isInstanceOf[Failure]
  }.groupBy(_.input.key).map {
    case (key, failures) => (key, failures.flatMap(_.errors))
  })

  def fold[A](errorsHandler: (Inputs, Errors) => A, inputsHandler: (Inputs) => A): A = {
    if (hasErrors) errorsHandler.apply(inputs, errors)
    else inputsHandler.apply(inputs)
  }

  def success[B](f: (Inputs) => B) = results.success[B](f)

  def failure[B](f: (Inputs, Errors) => B) = results.failure[B](f)

  def hasErrors: Boolean = !errors.isEmpty

  protected def extractValue(value: Any): Any = value match {
    case Some(v) => v
    case None => null
    case v => v
  }

}

