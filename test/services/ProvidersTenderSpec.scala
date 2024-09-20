package services

import model.{Belgium, Germany, Netherlands, Provider, Shipment, ShipmentCost, TenderResult, WeightRange}
import org.scalatestplus.play.PlaySpec

class ProvidersTenderSpec extends PlaySpec {

  case class CustomProvider(name: String, shipmentCosts: Seq[ShipmentCost]) extends Provider

  "calculate" must {

    "provide correct tender result" in {

      val provider1 = CustomProvider(
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

      val provider2 = CustomProvider(
        "Provider2",
        Seq(
          ShipmentCost(Netherlands, 100, WeightRange(0, 250)),
          ShipmentCost(Netherlands, 190, WeightRange(251, 500)),
          ShipmentCost(Belgium, 150, WeightRange(0, 250)),
          ShipmentCost(Belgium, 250, WeightRange(251, 500)),
          ShipmentCost(Germany, 100, WeightRange(0, 250)),
          ShipmentCost(Germany, 390, WeightRange(251, 500)),
        )
      )

      val provider3 = CustomProvider(
        "Provider3",
        Seq(
          ShipmentCost(Netherlands, 50, WeightRange(0, 100)),
          ShipmentCost(Netherlands, 80, WeightRange(101, 200)),
          ShipmentCost(Netherlands, 120, WeightRange(201, 300)),
          ShipmentCost(Netherlands, 200, WeightRange(301, 400)),
          ShipmentCost(Netherlands, 300, WeightRange(401, 500)),
          ShipmentCost(Belgium, 100, WeightRange(0, 100)),
          ShipmentCost(Belgium, 120, WeightRange(101, 200)),
          ShipmentCost(Belgium, 300, WeightRange(201, 300)),
          ShipmentCost(Belgium, 330, WeightRange(301, 400)),
          ShipmentCost(Belgium, 360, WeightRange(401, 500)),
          ShipmentCost(Germany, 40, WeightRange(0, 100)),
          ShipmentCost(Germany, 80, WeightRange(101, 200)),
          ShipmentCost(Germany, 130, WeightRange(201, 300)),
          ShipmentCost(Germany, 400, WeightRange(301, 400)),
          ShipmentCost(Germany, 450, WeightRange(401, 500)),
        )
      )

      val providers = Seq(provider1, provider2, provider3)

      val shipments = Seq(
        Shipment(1, Netherlands, 34),
        Shipment(2, Netherlands, 134),
        Shipment(3, Belgium, 49),
        Shipment(4, Belgium, 277),
        Shipment(5, Germany, 331),
        Shipment(6, Germany, 500),
      )

      val tender            = new ProvidersTender(providers, MainShipmentCostDetector)
      val tenderResultForNL = tender.calculate(Netherlands, shipments)
      val tenderResultForBE = tender.calculate(Belgium, shipments)
      val tenderResultForDE = tender.calculate(Germany, shipments)

      val expectedResultForNl = TenderResult(Netherlands, Map(provider1 -> 160, provider2 -> 200, provider3 -> 130))
      val expectedResultForBE = TenderResult(Belgium, Map(provider1 -> 315, provider2 -> 400, provider3 -> 400))
      val expectedResultForDE = TenderResult(Germany, Map(provider1 -> 880, provider2 -> 780, provider3 -> 850))

      tenderResultForNL mustBe Some(expectedResultForNl)
      tenderResultForBE mustBe Some(expectedResultForBE)
      tenderResultForDE mustBe Some(expectedResultForDE)
    }

  }

}
