import com.google.inject.AbstractModule
import providers.{FileProcessorProvider, TenderProvider, TenderResultAggregatorProvider}
import services.{Tender, TenderResultAggregator}
import services.csv.FileProcessor

class StartModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[FileProcessor]).toProvider(classOf[FileProcessorProvider])
    bind(classOf[Tender]).toProvider(classOf[TenderProvider])
    bind(classOf[TenderResultAggregator]).toProvider(classOf[TenderResultAggregatorProvider])
  }
}
