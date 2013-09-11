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

  def booleanOpt(key: String): Option[Boolean] = inputMap.get(key).map(_.asInstanceOf[Boolean])
  def boolean(key: String): Boolean = booleanOpt(key).get

  def byteOpt(key: String): Option[Byte] = inputMap.get(key).map(_.asInstanceOf[Byte])
  def byte(key: String): Byte = byteOpt(key).get

  def doubleOpt(key: String): Option[Double] = inputMap.get(key).map(_.asInstanceOf[Double])
  def double(key: String): Double = doubleOpt(key).get

  def floatOpt(key: String): Option[Float] = inputMap.get(key).map(_.asInstanceOf[Float])
  def float(key: String): Float = floatOpt(key).get

  def intOpt(key: String): Option[Int] = inputMap.get(key).map(_.asInstanceOf[Int])
  def int(key: String): Int = intOpt(key).get

  def longOpt(key: String): Option[Long] = inputMap.get(key).map(_.asInstanceOf[Long])
  def long(key: String): Long = longOpt(key).get

  def shortOpt(key: String): Option[Short] = inputMap.get(key).map(_.asInstanceOf[Short])
  def short(key: String): Short = shortOpt(key).get

  def stringOpt(key: String): Option[String] = inputMap.get(key).map(_.asInstanceOf[String])
  def string(key: String): String = stringOpt(key).get

}

case class InputsFromResults(results: Validations) extends Inputs {
  override protected val inputMap: Map[String, Any] = results.toMap()
}

case class InputsFromMap(map: Map[String, Any]) extends Inputs {
  override protected val inputMap: Map[String, Any] = map
}

