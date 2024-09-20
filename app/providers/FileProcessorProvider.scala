package providers

import services.csv.{CsvFileProcessor, FileProcessor}

import javax.inject.{Provider, Singleton}

@Singleton
class FileProcessorProvider extends Provider[FileProcessor] {
  override def get(): FileProcessor = CsvFileProcessor
}
