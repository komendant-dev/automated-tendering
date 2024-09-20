package providers

import play.api.Configuration
import services.{MainShipmentCostDetector, ProvidersTender, Tender}

import javax.inject.{Inject, Provider, Singleton}

@Singleton
class TenderProvider @Inject() (config: Configuration) extends Provider[Tender] {
  private val supportedProviders =
    config.get[Seq[String]]("supportedProviders").flatMap(model.Provider.detect)
  override def get(): Tender = new ProvidersTender(supportedProviders, MainShipmentCostDetector)
}
