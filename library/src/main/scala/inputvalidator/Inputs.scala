package inputvalidator

sealed trait Inputs {

  protected val inputMap: Map[String, Any]

  def keys(): Seq[String] = toSeq().map(_.key)
  def values(): Seq[Any] = toSeq().map(_.value)

  def toMap(): Map[String, Any] = inputMap
  def toSeq(): Seq[Input] = inputMap.toSeq.map { case (k, v) => KeyValueInput(k, v) }

  def getOpt(key: String): Option[Any] = inputMap.get(key)
  def get(key: String): Any = inputMap.getOrElse(key, null)
  def getOrElse[A](key: String, default: A): A = inputMap.get(key).map(_.asInstanceOf[A]).getOrElse(default)

  def booleanOpt(key: String): Option[Boolean] = getOpt(key).map { v =>
    try {
      v.asInstanceOf[Boolean]
    } catch {
      case e: ClassCastException =>
        java.lang.Boolean.parseBoolean(v.toString)
    }
  }

  def byteOpt(key: String) = getOpt(key).map { v =>
    try {
      v.asInstanceOf[Byte]
    } catch {
      case e: ClassCastException =>
        java.lang.Byte.parseByte(v.toString)
    }
  }

  def doubleOpt(key: String) = getOpt(key).map { v =>
    try {
      v.asInstanceOf[Double]
    } catch {
      case e: ClassCastException =>
        java.lang.Double.parseDouble(v.toString)
    }
  }

  def floatOpt(key: String) = getOpt(key).map { v =>
    try {
      v.asInstanceOf[Float]
    } catch {
      case e: ClassCastException =>
        java.lang.Float.parseFloat(v.toString)
    }
  }

  def intOpt(key: String) = getOpt(key).map { v =>
    try {
      v.asInstanceOf[Int]
    } catch {
      case e: ClassCastException =>
        java.lang.Integer.parseInt(v.toString)
    }
  }

  def longOpt(key: String) = getOpt(key).map { v =>
    try {
      v.asInstanceOf[Long]
    } catch {
      case e: ClassCastException =>
        java.lang.Long.parseLong(v.toString)
    }
  }

  def shortOpt(key: String) = getOpt(key).map { v =>
    try {
      v.asInstanceOf[Short]
    } catch {
      case e: ClassCastException =>
        java.lang.Short.parseShort(v.toString)
    }
  }

  def stringOpt(key: String) = getOpt(key).filter(_ != null).map(_.toString)

  def boolean(key: String): Boolean = booleanOpt(key).get
  def byte(key: String): Byte = byteOpt(key).get
  def double(key: String): Double = doubleOpt(key).get
  def float(key: String): Float = floatOpt(key).get
  def int(key: String): Int = intOpt(key).get
  def long(key: String): Long = longOpt(key).get
  def short(key: String): Short = shortOpt(key).get
  def string(key: String): String = stringOpt(key).orNull[String]

}

case class InputsFromResults(results: Validations) extends Inputs {
  override protected val inputMap: Map[String, Any] = results.toMap()
}

case class InputsFromMap(map: Map[String, Any]) extends Inputs {
  override protected val inputMap: Map[String, Any] = map
}

