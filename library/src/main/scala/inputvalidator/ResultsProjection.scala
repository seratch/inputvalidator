package inputvalidator

object ResultsProjection {

  val defaultOnSuccess: (Inputs) => Nothing = {
    (inputs: Inputs) => throw new IllegalStateException("onSuccess handler is not specified.")
  }

  val defaultOnFailures: (Inputs, Errors) => Nothing = {
    (inputs: Inputs, errors: Errors) => throw new IllegalStateException("onFailures handler is not specified.")
  }

}

sealed trait ResultsProjection[+A] {

  val results: Validations

  val onSuccess: (Inputs) => A

  val onFailures: (Inputs, Errors) => A

  def success[B >: A](f: (Inputs) => B): SuccessesProjection[B] = {
    SuccessesProjection(results, onSuccess, onFailures).map(f)
  }

  def failure[B >: A](f: (Inputs, Errors) => B): FailuresProjection[B] = {
    FailuresProjection(results, onSuccess, onFailures).map(f)
  }

  def apply(): A = {
    if (results.isSuccess) onSuccess(results.inputs)
    else onFailures(results.inputs, results.errors)
  }

}

case class SuccessesProjection[+A](override val results: Validations,
    override val onSuccess: (Inputs) => A,
    override val onFailures: (Inputs, Errors) => A) extends ResultsProjection[A] {

  def map[B >: A](f: (Inputs) => B): SuccessesProjection[B] = {
    SuccessesProjection[B](results, f, onFailures.asInstanceOf[(Inputs, Errors) => B])
  }

}

case class FailuresProjection[+A](override val results: Validations,
    override val onSuccess: (Inputs) => A,
    override val onFailures: (Inputs, Errors) => A) extends ResultsProjection[A] {

  def map[B >: A](f: (Inputs, Errors) => B): FailuresProjection[B] = {
    FailuresProjection[B](results, onSuccess.asInstanceOf[(Inputs) => B], f)
  }

}

