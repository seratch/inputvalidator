package inputvalidator

// ----
// input("x" -> "") is notNull

object notNull extends Validation {
  def name = "notNull"
  def isValid(v: Any) = v != null
}

// ----
// input("x" -> "y") is required

object required extends Validation {
  private[this] val instance = required()
  def name = instance.name
  def isValid(v: Any) = instance.isValid(v)
}

case class required(trim: Boolean = true) extends Validation {
  def name = "required"
  def isValid(v: Any) = v != null && {
    if (trim) v.toString.trim.length > 0
    else v.toString.length > 0
  }
}

// ----
// input("x" -> "y") is notEmpty
// input("list" -> Seq(1,2,3)) is notEmpty

object notEmpty extends Validation {
  private[this] val instance = notEmpty()
  def name = instance.name
  def isValid(v: Any) = instance.isValid(v)
}
case class notEmpty(trim: Boolean = true) extends Validation {
  def name = "notEmpty"
  def isValid(v: Any) = v != null && {
    utils.toHasSize(v).map {
      x => x.size > 0
    }.getOrElse {
      if (trim) v.toString.trim.length > 0
      else v.toString.length > 0
    }
  }
}

case class length(len: Int) extends Validation {
  def name = "length"
  override def messageParams = Seq(len.toString)
  def isValid(v: Any) = v != null && {
    utils.toHasSize(v).map {
      x => x.size == len
    }.getOrElse {
      v.toString.length == len
    }
  }
}

// ----
// input("x" -> "yyyymmdd") is minLength(3)
// input("list" -> (1 to 5)) is minLength(3)

case class minLength(min: Int) extends Validation {
  def name = "minLength"
  override def messageParams = Seq(min.toString)
  def isValid(v: Any) = v != null && {
    utils.toHasSize(v).map {
      x => x.size >= min
    }.getOrElse {
      v.toString.length >= min
    }
  }
}

// ----
// input("x" -> "y") is maxLength(3)
// input("list" -> Seq(1,2)) is maxLength(3)

case class maxLength(max: Int) extends Validation {
  def name = "maxLength"
  override def messageParams = Seq(max.toString)
  def isValid(v: Any) = v != null && {
    utils.toHasSize(v).map {
      x => x.size <= max
    }.getOrElse {
      v.toString.length <= max
    }
  }
}

// ----
// input("x" -> "y") is minMaxLength(3, 6)
// input("list" -> Seq(1,2,3,4)) is minMaxLength(3, 6)

case class minMaxLength(min: Int, max: Int) extends Validation {
  def name = "minMaxLength"
  override def messageParams = Seq(min.toString, max.toString)
  def isValid(v: Any) = v != null && {
    utils.toHasSize(v).map {
      x => x.size >= min && x.size <= max
    }.getOrElse {
      v.toString.length >= min && v.toString.length <= max
    }
  }
}

// ----
// input("x" -> "123") is numeric
// input("x" -> 0.123D) is numeric

object numeric extends Validation {
  def name = "numeric"
  def isValid(v: Any) = v != null &&
    "^((-|\\+)?[0-9]+(\\.[0-9]+)?)+$".r.findFirstIn(v.toString).isDefined
}

// ----
// input("x" -> 4) is intMinMaxValue(3, 5)

case class intMinMaxValue(min: Int, max: Int) extends Validation {
  def name = "intMinMaxValue"
  override def messageParams = Seq(min.toString, max.toString)
  def isValid(v: Any) = v != null && v.toString.toInt >= min && v.toString.toInt <= max
}

// ----
// input("x" -> 2) is intMinValue(3)

case class intMinValue(min: Int) extends Validation {
  def name = "intMinValue"
  override def messageParams = Seq(min.toString)
  def isValid(v: Any) = v != null && v.toString.toInt >= min
}

// ----
// input("x" -> 4) is intMaxValue(5)

case class intMaxValue(max: Int) extends Validation {
  def name = "intMaxValue"
  override def messageParams = Seq(max.toString)
  def isValid(v: Any) = v != null && v.toString.toInt <= max
}

// ----
// input("x" -> "3") is longMinMaxValue(3L, 5L)

case class longMinMaxValue(min: Long, max: Long) extends Validation {
  def name = "longMinMaxValue"
  override def messageParams = Seq(min.toString, max.toString)
  def isValid(v: Any) = v != null && v.toString.toLong >= min && v.toString.toLong <= max
}

// ----
// input("x" -> 5) is longMinValue(3L)

case class longMinValue(min: Long) extends Validation {
  def name = "longMinValue"
  override def messageParams = Seq(min.toString)
  def isValid(v: Any) = v != null && v.toString.toLong >= min
}

// ----
// input("x" -> 1.0D) is longMaxValue(5L)

case class longMaxValue(max: Long) extends Validation {
  def name = "longMaxValue"
  override def messageParams = Seq(max.toString)
  def isValid(v: Any) = v != null && v.toString.toLong <= max
}

// ----
// input("pair" -> ("pass", "pass")) are same

object same extends Validation {
  def name = "same"
  def isValid(pair: Any) = {
    val (a, b) = pair.asInstanceOf[(Any, Any)]
    if (a.isInstanceOf[Option[_]] && b.isInstanceOf[Option[_]]) {
      val (x, y) = pair.asInstanceOf[(Option[Any], Option[Any])]
      (x.isEmpty && y.isEmpty) || (x.isDefined && y.isDefined && x.get == y.get)
    } else {
      a == b
    }
  }
}

// ----
// input("email" -> "alice@example.com") is email
// [NOTE] This is not a complete solution

object email extends Validation {
  def name = "email"
  def isValid(v: Any) = v != null &&
    """^([^@\s]+)@((?:[-a-z0-9]+\.)+[a-z]{2,})$""".r.findFirstIn(v.toString).isDefined
}

// ----
// input("time" -> new java.util.Date(123L)) is past
// input("time" -> org.joda.time.DateTime.now.minusDays(3)) is past

object past extends Validation {
  def name = "past"
  def isValid(v: Any): Boolean = {
    if (v != null) {
      utils.toHasGetTime(v) match {
        case Some(time) => time.getTime < utils.nowMillis()
        case _ => false
      }
    } else false
  }
}

// ----
// input("time" -> new java.util.Date) is future

object future extends Validation {
  def name = "future"
  def isValid(v: Any): Boolean = {
    if (v != null) {
      utils.toHasGetTime(v) match {
        case Some(time) => time.getTime > utils.nowMillis()
        case _ => false
      }
    } else false
  }
}

private[inputvalidator] object utils {

  def toHasSize(v: Any): Option[{ def size(): Int }] = {
    Option.apply {
      try {
        val x = v.asInstanceOf[{ def size(): Int }]
        x.size
        x
      } catch {
        case e: NoSuchMethodException => null
      }
    }
  }

  def toHasGetTime(v: Any): Option[{ def getTime(): Long }] = {
    Option.apply {
      try {
        v.asInstanceOf[{ def toDate(): java.util.Date }].toDate
      } catch {
        case e: NoSuchMethodException =>
          try {
            val x = v.asInstanceOf[{ def getTime(): Long }]
            x.getTime
            x
          } catch {
            case e: NoSuchMethodException =>
              null
          }
      }
    }
  }

  def nowMillis(): Long = System.currentTimeMillis

}

