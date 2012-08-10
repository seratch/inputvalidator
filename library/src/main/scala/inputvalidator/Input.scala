package inputvalidator

sealed trait Input {

  val key: String
  val value: Any

}

case class KeyInput(override val key: String) extends Input {
  override lazy val value: Any = throw new IllegalStateException
}

case class KeyValueInput(override val key: String, override val value: Any) extends Input

