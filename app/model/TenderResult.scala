package model

case class TenderResult(country: Country, result: Map[Provider, Long]) {
  val cheapestOption: Option[(Provider, Long)] =
    if (result.nonEmpty) Some(result.toSeq.minBy(_._2)) else None
}
