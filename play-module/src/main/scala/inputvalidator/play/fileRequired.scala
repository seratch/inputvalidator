package inputvalidator.play

import _root_.play.api.mvc.MultipartFormData.FilePart

object fileRequired extends inputvalidator.ValidationRule {

  def name = "fileRequired"

  def isValid(file: Any) = {
    file.isInstanceOf[FilePart[_]] && file.asInstanceOf[FilePart[_]].filename != null
  }

}
