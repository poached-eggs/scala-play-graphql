package controllers

import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import javax.inject._

@Singleton
class HealthController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  val healthCheck: Action[AnyContent] = Action(Ok("OK"))

}
