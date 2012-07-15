package inputvalidator

sealed trait Inputs {

  protected val inputMap: Map[String, Any]

  def keys(): Seq[String] = toSeq().map(_.key)

  def values(): Seq[Any] = toSeq().map(_.value)

  def toMap(): Map[String, Any] = inputMap

  def toSeq(): Seq[Input] = inputMap.toSeq.map { case (k, v) => KeyValueInput(k, v) }

  def get(key: String): Option[Any] = inputMap.get(key)

  def getOrElse[A](key: String, default: A): A = inputMap.get(key).map(_.asInstanceOf[A]).getOrElse(default)

  def boolean(key: String) = inputMap.get(key).asInstanceOf[Option[Boolean]]

  def byte(key: String) = inputMap.get(key).asInstanceOf[Option[Byte]]

  def double(key: String) = inputMap.get(key).asInstanceOf[Option[Double]]

  def float(key: String) = inputMap.get(key).asInstanceOf[Option[Float]]

  def int(key: String) = inputMap.get(key).asInstanceOf[Option[Int]]

  def long(key: String) = inputMap.get(key).asInstanceOf[Option[Long]]

  def short(key: String) = inputMap.get(key).asInstanceOf[Option[Short]]

  def string(key: String) = inputMap.get(key).asInstanceOf[Option[String]]

}

case class InputsFromResults(results: Results) extends Inputs {

  override protected val inputMap: Map[String, Any] = results.toMap()

}

case class InputsFromMap(map: Map[String, Any]) extends Inputs {

  override protected val inputMap: Map[String, Any] = map

}

