package inputvalidator

case class MapValidator(map: Map[String, Any] = Map(), override val results: Results = Results(Map(), Nil)) extends ValidatorLike {

  override lazy val inputs: Inputs = InputsFromMap(map)

  def apply(results: NotYet*): MapValidator = {

    val mutableMap = collection.mutable.Map(map.toSeq: _*)

    val newResultSeq = results.toSeq ++ results.map {

      case NotYet(k: KeyInput, validations) =>
        if (!mutableMap.contains(k.key)) {
          mutableMap.update(k.key, extractValue(map.get(k.key)))
        }
        validations.apply(KeyValueInput(k.key, extractValue(map.get(k.key))))

      case NotYet(kv: KeyValueInput, validations) =>
        if (!mutableMap.contains(kv.key)) {
          mutableMap.update(kv.key, kv.value)
        }
        validations.apply(KeyValueInput(kv.key, extractValue(kv.value)))

      case done => done

    }

    val newMap = Map(mutableMap.toSeq: _*)
    MapValidator(newMap, Results(newMap, newResultSeq))

  }

}

