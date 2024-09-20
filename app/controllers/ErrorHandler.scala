package controllers

import play.api.Configuration
import play.api.http.DefaultHttpErrorHandler
import play.api.http.Status._
import play.api.mvc.Results._
import play.api.mvc._

import javax.inject._
import scala.concurrent.Future

@Singleton
class ErrorHandler @Inject() (config: Configuration) extends DefaultHttpErrorHandler {

  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] =
    statusCode match {
      case REQUEST_ENTITY_TOO_LARGE =>
        val maxFileSize = config.get[String]("play.http.multipart.maxFileSize")
        val error       = s"File too large. Maximum allowed size is $maxFileSize."
        Future.successful(Status(REQUEST_ENTITY_TOO_LARGE)(error))
      case _ =>
        Future.successful(Status(statusCode)(message))
    }

}
