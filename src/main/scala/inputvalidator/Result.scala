package inputvalidator

sealed trait Result {

  val input: Input
  val errors: Seq[Error] = Nil

  def isDone: Boolean = true
  def isSuccess: Boolean = true
  def isFailure: Boolean = !isSuccess

  def success: Success = this.asInstanceOf[Success]
  def failure: Failure = this.asInstanceOf[Failure]

  def toEither: Either[Failure, Success]

  def toSuccessOption: Option[Success] = None
  def toFailureOption: Option[Failure] = None

}

case class NotYet(override val input: Input, validations: Validation) extends Result {
  override def isDone: Boolean = false
  def toEither: Either[Failure, Success] = throw new IllegalStateException
}

case class Success(override val input: Input) extends Result {
  def toEither: Either[Failure, Success] = Right(this)
  override def toSuccessOption: Option[Success] = Some(this)
}

case class Failure(override val input: Input, override val errors: Seq[Error]) extends Result {
  override def isSuccess: Boolean = false
  def toEither: Either[Failure, Success] = Left(this)
  override def toFailureOption: Option[Failure] = Some(this)
}

