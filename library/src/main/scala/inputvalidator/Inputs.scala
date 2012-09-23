package inputvalidator

sealed trait Inputs {

  protected val inputMap: Map[String, Any]

  def keys(): Seq[String] = toSeq().map(_.key)

  def values(): Seq[Any] = toSeq().map(_.value)

  def toMap(): Map[String, Any] = inputMap

  def toSeq(): Seq[Input] = inputMap.toSeq.map { case (k, v) => KeyValueInput(k, v) }

  def get(key: String): Option[Any] = inputMap.get(key)

  def getOrElse[A](key: String, default: A): A = inputMap.get(key).map(_.asInstanceOf[A]).getOrElse(default)

  def boolean(key: String): Option[Boolean] = get(key).map { v =>
    try {
      v.asInstanceOf[Boolean]
    } catch {
      case e: ClassCastException =>
        java.lang.Boolean.parseBoolean(v.toString)
    }
  }

  def byte(key: String) = get(key).map { v =>
    try {
      v.asInstanceOf[Byte]
    } catch {
      case e: ClassCastException =>
        java.lang.Byte.parseByte(v.toString)
    }
  }

  def double(key: String) = get(key).map { v =>
    try {
      v.asInstanceOf[Double]
    } catch {
      case e: ClassCastException =>
        java.lang.Double.parseDouble(v.toString)
    }
  }

  def float(key: String) = get(key).map { v =>
    try {
      v.asInstanceOf[Float]
    } catch {
      case e: ClassCastException =>
        java.lang.Float.parseFloat(v.toString)
    }
  }

  def int(key: String) = get(key).map { v =>
    try {
      v.asInstanceOf[Int]
    } catch {
      case e: ClassCastException =>
        java.lang.Integer.parseInt(v.toString)
    }
  }

  def long(key: String) = get(key).map { v =>
    try {
      v.asInstanceOf[Long]
    } catch {
      case e: ClassCastException =>
        java.lang.Long.parseLong(v.toString)
    }
  }

  def short(key: String) = get(key).map { v =>
    try {
      v.asInstanceOf[Short]
    } catch {
      case e: ClassCastException =>
        java.lang.Short.parseShort(v.toString)
    }
  }

  def string(key: String) = inputMap.get(key).asInstanceOf[Option[String]]

}

case class InputsFromResults(results: Results) extends Inputs {

  override protected val inputMap: Map[String, Any] = results.toMap()

}

case class InputsFromMap(map: Map[String, Any]) extends Inputs {

  override protected val inputMap: Map[String, Any] = map

}

