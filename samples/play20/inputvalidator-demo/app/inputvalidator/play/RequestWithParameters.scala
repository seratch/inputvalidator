package inputvalidator.play

import play.api.mvc.{ AnyContentAsMultipartFormData, MultipartFormData, AnyContent, Request }
import play.api.mvc.MultipartFormData._

case class RequestWithParameters(req: Request[_]) {

  def parameters: Map[String, Any] = {

    req.body match {

      case AnyContentAsMultipartFormData(mdf) => mdf match {
        case MultipartFormData(dataParts: Map[_, _], files: Seq[_], _, _) =>
          val parameters: Map[String, Any] = dataParts.map {
            case (k, Nil) => (k.toString, null)
            case (k, v: Seq[_]) if v.size == 1 => (k.toString, v.head)
            case (k, v: Seq[_]) => (k.toString, v)
          } ++ files.map {
            case file: FilePart[_] => (file.key, file.asInstanceOf[Any])
            case something => throw new IllegalArgumentException("Unknown parameter (" + something + ")")
          }
          parameters
        case body =>
          throwsUnknownBodyException(body)
      }

      case anyContent: AnyContent if anyContent.asFormUrlEncoded.isDefined =>
        anyContent.asFormUrlEncoded.get.map {
          case (key, vs) if vs.size > 1 => (key, vs)
          case (key, v) if v.size == 1 => (key, v.head)
          case (key, v) => (key, v)
        }

      case body => throwsUnknownBodyException(body)

    }

  }

  private def throwsUnknownBodyException(body: Any): Map[String, Any] = {
    throw new IllegalArgumentException(
      "The request body does not contain form parameters. (" + body + ")")
  }

}
