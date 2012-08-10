package inputvalidator.play

import inputvalidator.Validation
import play.api.mvc.MultipartFormData.FilePart

object fileRequired extends Validation {

  def name = "fileRequired"

  def isValid(file: Any) = {
    file.isInstanceOf[FilePart[_]] && file.asInstanceOf[FilePart[_]].filename != null
  }

}
