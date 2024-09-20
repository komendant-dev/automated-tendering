package services

import model.{Country, Provider, Shipment, TenderResult}

trait Tender {
  def calculate(country: Country, shipments: Seq[Shipment]): Option[TenderResult]
}

class ProvidersTender(
    providers: Seq[Provider],
    shipmentCostDetector: ShipmentCostDetector,
) extends Tender {

  def calculate(country: Country, shipments: Seq[Shipment]): Option[TenderResult] = {
    val filtered = shipments.filter(_.country == country)
    if (filtered.nonEmpty) {
      val costsPerProvider = providers.map { provider =>
        val totalCost = filtered.map(shipment => shipmentCostDetector.detect(shipment, provider).cost).sum
        (provider, totalCost)
      }
      Some(TenderResult(country, costsPerProvider.toMap))
    } else {
      None
    }
  }

}
