package inputvalidator

case class MapValidator(map: Map[String, Any] = Map(), override val results: Results = Results(Nil)) extends ValidatorLike {

  def apply(inputs: NotYet*): MapValidator = {
    val newResults = Results(results.toSeq ++ inputs.map {
      case NotYet(k: KeyInput, validations) =>
        validations.apply(KeyValueInput(k.key, extractValue(map.get(k.key))))
      case NotYet(kv: KeyValueInput, validations) =>
        validations.apply(KeyValueInput(kv.key, extractValue(kv.value)))
      case done => done
    })
    MapValidator(newResults.inputs.toMap(), newResults)
  }

}

