package inputvalidator

object Validator {

  def apply(map: Map[String, Any]): MapValidator = {
    new MapValidator(map)
  }

  def apply(inputs: NotYet*): Validator = {
    new Validator().apply(inputs: _*)
  }

}

case class Validator(override val results: Results = Results(Nil)) extends ValidatorLike {

  def apply(inputs: NotYet*): Validator = {
    Validator(Results(results.toSeq ++ inputs.map {
      case NotYet(kv: KeyValueInput, vs: Validation) =>
        vs.apply(KeyValueInput(kv.key, extractValue(kv.value)))
      case done => done
    }))
  }

}

