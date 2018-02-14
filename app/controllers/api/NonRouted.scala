package controllers.api

import javax.inject.{Inject, Singleton}

import akka.actor.ActorSystem
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

import scala.concurrent.ExecutionContext

@Singleton
class NonRouted @Inject()(actorSystem: ActorSystem)(implicit exec: ExecutionContext) extends Controller {
  def notFound(path: String) = Action {
    NotFound(Json.obj("message" -> "The requested resource corresponding to the method was not found."))
  }
}
