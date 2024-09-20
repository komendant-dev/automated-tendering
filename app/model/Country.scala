package model

sealed trait Country {
  val code: String
  val name: String
}

object Country {
  def detect(code: String): Option[Country] =
    code match {
      case Netherlands.code => Some(Netherlands)
      case Belgium.code     => Some(Belgium)
      case Germany.code     => Some(Germany)
      case _                => None
    }
}

case object Netherlands extends Country {
  val code: String = "NL"
  val name: String = "Netherlands"
}

case object Belgium extends Country {
  val code: String = "BE"
  val name: String = "Belgium"
}

case object Germany extends Country {
  val code: String = "DE"
  val name: String = "Germany"
}
