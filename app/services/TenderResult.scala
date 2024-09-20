package services

import model.{Provider, TenderResult}

trait TenderResultAggregator {
  def aggregate(results: Seq[TenderResult]): Map[Provider, Long]
}

object MainTenderResultAggregator extends TenderResultAggregator {
  def aggregate(results: Seq[TenderResult]): Map[Provider, Long] =
    results
      .flatMap(_.result)
      .groupBy(_._1)
      .map { case (provider, costs) => (provider, costs.map(_._2).sum) }
}
