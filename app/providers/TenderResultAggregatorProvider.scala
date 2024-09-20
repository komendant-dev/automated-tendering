package providers

import services.{MainTenderResultAggregator, TenderResultAggregator}

import javax.inject.{Provider, Singleton}

@Singleton
class TenderResultAggregatorProvider extends Provider[TenderResultAggregator] {
  override def get(): TenderResultAggregator = MainTenderResultAggregator
}
