package model

trait Provider {
  val name: String
  val shipmentCosts: Seq[ShipmentCost]
}

object Provider {
  def detect(name: String): Option[Provider] =
    name match {
      case ProviderA.name => Some(ProviderA)
      case ProviderB.name => Some(ProviderB)
      case ProviderC.name => Some(ProviderC)
      case _              => None
    }
}

case object ProviderA extends Provider {
  val name: String = "Provider A"
  val shipmentCosts: Seq[ShipmentCost] = Seq(
    ShipmentCost(Netherlands, 50, WeightRange(0, 100)),
    ShipmentCost(Netherlands, 110, WeightRange(101, 250)),
    ShipmentCost(Netherlands, 170, WeightRange(251, 500)),
    ShipmentCost(Netherlands, 290, WeightRange(501, 750)),
    ShipmentCost(Netherlands, 650, WeightRange(751, 1000)),
    ShipmentCost(Belgium, 75, WeightRange(0, 100)),
    ShipmentCost(Belgium, 160, WeightRange(101, 250)),
    ShipmentCost(Belgium, 240, WeightRange(251, 500)),
    ShipmentCost(Belgium, 480, WeightRange(501, 750)),
    ShipmentCost(Belgium, 700, WeightRange(751, 1000)),
    ShipmentCost(Germany, 50, WeightRange(0, 100)),
    ShipmentCost(Germany, 150, WeightRange(101, 250)),
    ShipmentCost(Germany, 440, WeightRange(251, 500)),
    ShipmentCost(Germany, 780, WeightRange(501, 750)),
    ShipmentCost(Germany, 900, WeightRange(751, 1000)),
  )
}

case object ProviderB extends Provider {
  val name: String = "Provider B"
  val shipmentCosts: Seq[ShipmentCost] = Seq(
    ShipmentCost(Netherlands, 100, WeightRange(0, 250)),
    ShipmentCost(Netherlands, 190, WeightRange(251, 500)),
    ShipmentCost(Netherlands, 270, WeightRange(501, 750)),
    ShipmentCost(Netherlands, 350, WeightRange(751, 1000)),
    ShipmentCost(Belgium, 150, WeightRange(0, 250)),
    ShipmentCost(Belgium, 250, WeightRange(251, 500)),
    ShipmentCost(Belgium, 490, WeightRange(501, 750)),
    ShipmentCost(Belgium, 650, WeightRange(751, 1000)),
    ShipmentCost(Germany, 100, WeightRange(0, 250)),
    ShipmentCost(Germany, 390, WeightRange(251, 500)),
    ShipmentCost(Germany, 680, WeightRange(501, 750)),
    ShipmentCost(Germany, 790, WeightRange(751, 1000)),
  )
}

case object ProviderC extends Provider {
  val name: String = "Provider C"
  val shipmentCosts: Seq[ShipmentCost] = Seq(
    ShipmentCost(Netherlands, 50, WeightRange(0, 100)),
    ShipmentCost(Netherlands, 80, WeightRange(101, 200)),
    ShipmentCost(Netherlands, 120, WeightRange(201, 300)),
    ShipmentCost(Netherlands, 200, WeightRange(301, 400)),
    ShipmentCost(Netherlands, 300, WeightRange(401, 500)),
    ShipmentCost(Netherlands, 500, WeightRange(501, 600)),
    ShipmentCost(Netherlands, 600, WeightRange(601, 700)),
    ShipmentCost(Netherlands, 800, WeightRange(701, 800)),
    ShipmentCost(Netherlands, 950, WeightRange(801, 900)),
    ShipmentCost(Netherlands, 950, WeightRange(901, 1000)),
    ShipmentCost(Belgium, 100, WeightRange(0, 100)),
    ShipmentCost(Belgium, 120, WeightRange(101, 200)),
    ShipmentCost(Belgium, 300, WeightRange(201, 300)),
    ShipmentCost(Belgium, 330, WeightRange(301, 400)),
    ShipmentCost(Belgium, 360, WeightRange(401, 500)),
    ShipmentCost(Belgium, 390, WeightRange(501, 600)),
    ShipmentCost(Belgium, 500, WeightRange(601, 700)),
    ShipmentCost(Belgium, 550, WeightRange(701, 800)),
    ShipmentCost(Belgium, 600, WeightRange(801, 900)),
    ShipmentCost(Belgium, 800, WeightRange(901, 1000)),
    ShipmentCost(Germany, 40, WeightRange(0, 100)),
    ShipmentCost(Germany, 80, WeightRange(101, 200)),
    ShipmentCost(Germany, 130, WeightRange(201, 300)),
    ShipmentCost(Germany, 400, WeightRange(301, 400)),
    ShipmentCost(Germany, 450, WeightRange(401, 500)),
    ShipmentCost(Germany, 500, WeightRange(501, 600)),
    ShipmentCost(Germany, 550, WeightRange(601, 700)),
    ShipmentCost(Germany, 600, WeightRange(701, 800)),
    ShipmentCost(Germany, 700, WeightRange(801, 900)),
    ShipmentCost(Germany, 850, WeightRange(901, 1000)),
  )
}
