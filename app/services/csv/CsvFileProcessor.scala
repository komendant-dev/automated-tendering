package services.csv

import com.github.tototoshi.csv.CSVReader
import model.{Country, Shipment, WeightRange}

import java.io.File
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, blocking}
import scala.util.{Failure, Success, Try}

object CsvFileProcessor extends FileProcessor {

  def process(file: File): Future[Either[Seq[FileError], Seq[Shipment]]] =
    Future {
      blocking {
        Try(CSVReader.open(file)) match {
          case Success(reader) =>
            try {
              val iterator = reader.iterator
              val (errors, shipments) = iterator.foldLeft((Seq.empty[CsvError], Seq.empty[Shipment])) {
                case ((accErrors, accShipments), row) =>
                  parseRow(row.toList) match {
                    case Left(error)     => (accErrors :+ error, accShipments)
                    case Right(shipment) => (accErrors, accShipments :+ shipment)
                  }
              }
              if (errors.nonEmpty) Left(errors) else Right(shipments)
            } catch {
              case _: Throwable => Left(Seq(defaultProcessingError))
            } finally
              reader.close()
          case Failure(_) => Left(Seq(defaultProcessingError))
        }
      }
    }

  private def defaultProcessingError: CsvError =
    CsvError(List.empty, "Something went wrong while processing CSV file.")

  private def parseRow(row: List[String]): Either[CsvError, Shipment] =
    if (row.length != 3) {
      Left(CsvError(row, "Row should have exactly 3 values - shipment_id,country_code,weight"))
    } else {
      val rawShipmentId :: countryCode :: rawWeight :: Nil = row
      val shipmentId                                       = rawShipmentId.strip.toLongOption
      val country                                          = Country.detect(countryCode.strip)
      val weight                                           = rawWeight.strip.toIntOption

      val error = if (shipmentId.isEmpty) {
        Some(s"Unexpected shipment ID: $rawShipmentId")
      } else if (country.isEmpty) {
        Some(s"Unsupported country code: $countryCode")
      } else if (weight.isEmpty) {
        Some(s"Unexpected weight value: $rawWeight")
      } else if (weight.get > WeightRange.maxAllowedWeight) {
        Some(s"Too high weight: $rawWeight. Allowed max weight is ${WeightRange.maxAllowedWeight}.")
      } else {
        None
      }

      error match {
        case Some(error) => Left(CsvError(row, error))
        case None        => Right(Shipment(shipmentId.get, country.get, weight.get))
      }
    }

}
