package inputvalidator

import java.text.MessageFormat
import java.util.Properties
import java.util.Locale

object Messages {

  private[this] val instance = Messages()

  def get(key: String): Option[String] = instance.get(key)

  def get(key: String, params: Seq[Any]): Option[String] = instance.get(key, params)

}

case class Messages(prefix: String = "messages", locale: Option[Locale] = None) {

  private val ext = ".properties"
  val file = locale.map { l => prefix + "_" + l.toString + ext }.getOrElse(prefix + ext)

  val properties = new Properties
  properties.load(this.getClass.getClassLoader.getResourceAsStream(file))

  def get(key: String): Option[String] = Option(properties.get(key)).map(_.toString)

  def get(key: String, params: Seq[Any]): Option[String] = get(key).map { template =>
    MessageFormat.format(template, params.map(_.asInstanceOf[java.lang.Object]): _*)
  }

}

