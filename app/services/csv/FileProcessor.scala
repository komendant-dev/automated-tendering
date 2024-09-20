package services.csv

import model.Shipment

import java.io.File
import scala.concurrent.Future

trait FileProcessor {
  def process(file: File): Future[Either[Seq[FileError], Seq[Shipment]]]
}
