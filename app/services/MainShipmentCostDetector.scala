package services

import model.{Provider, Shipment, ShipmentCost}

trait ShipmentCostDetector {
  def detect(shipment: Shipment, provider: Provider): ShipmentCost
}

object MainShipmentCostDetector extends ShipmentCostDetector {

  def detect(shipment: Shipment, provider: Provider): ShipmentCost =
    provider.shipmentCosts.find { cost =>
      cost.country == shipment.country &&
      cost.weightRange.contains(shipment.weight)
    }.get

}
