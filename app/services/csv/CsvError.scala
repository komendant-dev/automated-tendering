package services.csv

trait FileError {
  val error: String
}

case class CsvError(row: List[String], reason: String) extends FileError {
  val error: String = s"Row '${row.mkString(",")}' contains the following error: $reason"
}
