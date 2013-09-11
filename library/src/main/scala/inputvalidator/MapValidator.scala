package inputvalidator

case class MapValidator(map: Map[String, Any] = Map(), override val validations: Validations = Validations(Map(), Nil)) extends ValidatorLike {

  override lazy val inputs: Inputs = InputsFromMap(map)

  def apply(validations: NewValidation*): MapValidator = {
    val mutableMap = collection.mutable.Map(map.toSeq: _*)

    val newResults = validations.toSeq ++ validations.map {
      case NewValidation(k: KeyInput, validations) =>
        if (!mutableMap.contains(k.key)) {
          mutableMap.update(k.key, extractValue(map.get(k.key)))
        }
        validations.apply(KeyValueInput(k.key, extractValue(map.get(k.key))))

      case NewValidation(kv: KeyValueInput, validations) =>
        if (!mutableMap.contains(kv.key)) {
          mutableMap.update(kv.key, kv.value)
        }
        validations.apply(KeyValueInput(kv.key, extractValue(kv.value)))

      case done => done
    }

    val newMap = Map(mutableMap.toSeq: _*)
    MapValidator(newMap, Validations(newMap, newResults))

  }

}

