package controllers

import javax.inject._
import play.api._
import play.api.mvc._

/**
  * 2021-06-12 Issue when hitting routes pointing to this controller through Docker on Windows: Caused by:
  * java.lang.UnsupportedClassVersionError: controllers/routes has been compiled by a more recent version of the Java
  * Runtime (class file version 55.0), this version of the Java Runtime only recognizes class file versions up to 52.0
  */

/**
  * This controller creates an `Action` to handle HTTP requests to the application's home page.
  */
@Singleton
class HomeController @Inject() (
    val controllerComponents: ControllerComponents
  ) extends BaseController {

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method will be called when the application receives a `GET`
    * request with a path of `/`.
    */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }
}
