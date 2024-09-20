package model

import model.WeightRange.maxAllowedWeight

case class WeightRange(start: Int, end: Int) {
  require(start <= end, "Weight start must be less than or equal to end.")
  require(start >= 0, "Weight start must be equal to 0 or higher.")
  require(end <= maxAllowedWeight, s"Weight range shouldn't exceed $maxAllowedWeight.")

  def contains(weight: Int): Boolean = weight >= start && weight <= end
}

object WeightRange {
  val maxAllowedWeight = 1000
}
