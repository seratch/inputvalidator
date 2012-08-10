package inputvalidator.play

import play.api.mvc.Request

object Implicits {

  implicit def ToRequestWithParameters(req: Request[_]) = RequestWithParameters(req)

}
