package services

import model._
import org.scalatestplus.play.PlaySpec

class MainTenderResultAggregatorSpec extends PlaySpec {

  case class CustomProvider(name: String, shipmentCosts: Seq[ShipmentCost]) extends Provider

  "aggregate" must {

    "provide correct aggregation result" in {

      val tenderResult1 = TenderResult(
        Netherlands,
        Map(
          CustomProvider("Provider1", Seq()) -> 123,
          CustomProvider("Provider2", Seq()) -> 200,
          CustomProvider("Provider3", Seq()) -> 233,
        )
      )

      val tenderResult2 = TenderResult(
        Germany,
        Map(
          CustomProvider("Provider1", Seq()) -> 333,
          CustomProvider("Provider2", Seq()) -> 102,
          CustomProvider("Provider3", Seq()) -> 305,
        )
      )

      val tenderResult3 = TenderResult(
        Belgium,
        Map(
          CustomProvider("Provider1", Seq()) -> 324,
          CustomProvider("Provider2", Seq()) -> 242,
          CustomProvider("Provider3", Seq()) -> 293,
        )
      )

      val aggregated = MainTenderResultAggregator.aggregate(Seq(tenderResult1, tenderResult2, tenderResult3))

      aggregated mustBe Map(
        CustomProvider("Provider1", Seq()) -> 780,
        CustomProvider("Provider2", Seq()) -> 544,
        CustomProvider("Provider3", Seq()) -> 831,
      )
    }

  }

}
