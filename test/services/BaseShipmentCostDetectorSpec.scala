package services

import model._
import org.scalatestplus.play.PlaySpec

class BaseShipmentCostDetectorSpec extends PlaySpec {

  case class CustomProvider(name: String, shipmentCosts: Seq[ShipmentCost]) extends Provider

  private val provider = CustomProvider(
    "Provider1",
    Seq(
      ShipmentCost(Netherlands, 50, WeightRange(0, 100)),
      ShipmentCost(Netherlands, 110, WeightRange(101, 250)),
      ShipmentCost(Netherlands, 170, WeightRange(251, 500)),
      ShipmentCost(Belgium, 75, WeightRange(0, 100)),
      ShipmentCost(Belgium, 160, WeightRange(101, 250)),
      ShipmentCost(Belgium, 240, WeightRange(251, 500)),
      ShipmentCost(Germany, 50, WeightRange(0, 100)),
      ShipmentCost(Germany, 150, WeightRange(101, 250)),
      ShipmentCost(Germany, 440, WeightRange(251, 500)),
    )
  )

  "detect" must {

    "return correct cost" in {
      val shipment1 = Shipment(1, Netherlands, 350)
      MainShipmentCostDetector.detect(shipment1, provider) mustBe
        ShipmentCost(Netherlands, 170, WeightRange(251, 500))

      val shipment2 = Shipment(2, Belgium, 10)
      MainShipmentCostDetector.detect(shipment2, provider) mustBe
        ShipmentCost(Belgium, 75, WeightRange(0, 100))

      val shipment3 = Shipment(3, Germany, 362)
      MainShipmentCostDetector.detect(shipment3, provider) mustBe
        ShipmentCost(Germany, 440, WeightRange(251, 500))
    }

  }

}
