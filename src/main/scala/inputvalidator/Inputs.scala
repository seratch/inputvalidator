package inputvalidator

case class Inputs(results: Results) {

  private[this] lazy val map: Map[String, Any] = toMap()

  def filterSuccessesOnly(): Seq[Input] = results.filterSuccessesOnly().map(r => r.input)

  def filterFailuresOnly(): Seq[Input] = results.filterFailuresOnly().map(r => r.input)

  def keys(): Seq[String] = toSeq().map(_.key)

  def values(): Seq[Any] = toSeq().map(_.value)

  def toMap(): Map[String, Any] = results.toSeq.map(r => r.input).map(input => (input.key, input.value)).toMap

  def toSeq(): Seq[Input] = results.toSeq.map(r => r.input)

  def get(key: String): Option[Any] = map.get(key)

  def getOrElse[A](key: String, default: A): A = map.get(key).map(_.asInstanceOf[A]).getOrElse(default)

  def boolean(key: String) = map.get(key).asInstanceOf[Option[Boolean]]

  def byte(key: String) = map.get(key).asInstanceOf[Option[Byte]]

  def double(key: String) = map.get(key).asInstanceOf[Option[Double]]

  def float(key: String) = map.get(key).asInstanceOf[Option[Float]]

  def int(key: String) = map.get(key).asInstanceOf[Option[Int]]

  def long(key: String) = map.get(key).asInstanceOf[Option[Long]]

  def short(key: String) = map.get(key).asInstanceOf[Option[Short]]

  def string(key: String) = map.get(key).asInstanceOf[Option[String]]

}

