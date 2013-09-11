package inputvalidator

case class Form[A](validations: Validations, private val value: A) extends ValidatorLike {
  def get: A = value
}
