package inputvalidator

case class Results(results: Seq[Result]) {

  def inputs: Inputs = Inputs(this)

  def errors: Errors = Errors(filterErrorsOnly())

  def isSuccess: Boolean = filterFailuresOnly().isEmpty

  def filterSuccessesOnly(): Seq[Success] = results.filter(r => r.isInstanceOf[Success]).map(r => r.asInstanceOf[Success])

  def filterFailuresOnly(): Seq[Failure] = results.filter(r => r.isInstanceOf[Failure]).map(r => r.asInstanceOf[Failure])

  def filterErrorsOnly(): Map[String, Seq[Error]] = filterFailuresOnly().groupBy(_.input.key).map {
    case (key, fs) => (key, fs.flatMap(_.errors))
  }

  def success[A](f: (Inputs) => A): SuccessesProjection[A] = {
    SuccessesProjection[A](this, ResultsProjection.defaultOnSuccess, ResultsProjection.defaultOnFailures).map(f)
  }

  def failure[A](f: (Inputs, Errors) => A): FailuresProjection[A] = {
    FailuresProjection[A](this, ResultsProjection.defaultOnSuccess, ResultsProjection.defaultOnFailures).map(f)
  }

  def toSeq(): Seq[Result] = results

}

