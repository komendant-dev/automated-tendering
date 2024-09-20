package controllers

import model.Country
import play.api.Configuration
import play.api.libs.Files
import play.api.mvc.MultipartFormData.FilePart
import play.api.mvc._
import play.twirl.api.Html
import services.csv.FileProcessor
import services.{Tender, TenderResultAggregator}

import java.io.File
import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class TenderController @Inject() (
    val controllerComponents: ControllerComponents,
    config: Configuration,
    fileProcessor: FileProcessor,
    tender: Tender,
    tenderResultAggregator: TenderResultAggregator,
) extends BaseController {

  private val supportedCountries =
    config.get[Seq[String]]("supportedCountries").flatMap(Country.detect)

  def index(): Action[AnyContent] = Action { implicit request: Request[_] =>
    Ok(views.html.main())
  }

  def processFile: Action[MultipartFormData[Files.TemporaryFile]] =
    Action(parse.multipartFormData).async { request =>
      if (supportedCountries.isEmpty) {
        val error = "Currently we don't support any country. Please, try again later."
        Future.successful(Ok(views.html.main(errors = Seq(error))(request)))
      } else {
        request.body.file("csvFile") match {
          case Some(file) if isCsvFile(file) =>
            val template = processCsvFile(file.ref)(request)
            template.map(Ok(_))
          case Some(_) =>
            Future.successful(BadRequest("Please, upload CSV file. Other file types are not supported."))
          case None =>
            Future.successful(BadRequest("Missing CSV file."))
        }
      }
    }

  private def processCsvFile(file: File)(implicit request: RequestHeader): Future[Html] = {
    val processingResult = fileProcessor.process(file)
    processingResult.map { result =>
      result.fold(
        errors => views.html.main(errors = errors.map(_.error))(request),
        shipments => {
          val tenderResults    = supportedCountries.flatMap(tender.calculate(_, shipments))
          val aggregatedResult = tenderResultAggregator.aggregate(tenderResults)
          views.html.main(tenderResults, aggregatedResult, Seq())(request)
        }
      )
    }
  }

  private def isCsvFile(file: FilePart[_]): Boolean =
    file.filename.toLowerCase.endsWith(".csv")

}
