package inputvalidator

object Validator {

  def apply(map: Map[String, Any]): MapValidator = {
    new MapValidator(map)
  }

  def apply(validations: NewValidation*): Validator = {
    new Validator().apply(validations: _*)
  }

}

case class Validator(override val validations: Validations = Validations(Map(), Nil)) extends ValidatorLike {

  def apply(newValidations: NewValidation*): Validator = {
    val mergedValidations = validations.toSeq ++ newValidations.map {
      case NewValidation(kv: KeyValueInput, vs: ValidationRule) =>
        vs.apply(KeyValueInput(kv.key, extractValue(kv.value)))
      case done => done
    }
    Validator(Validations(
      inputMap = Map(mergedValidations.map { r => (r.input.key, r.input.value) }: _*),
      validations = mergedValidations
    ))
  }

}

