package controllers

import javax.inject.{Inject, Singleton}

import akka.actor.ActorSystem
import play.api.mvc.Controller

import scala.concurrent.ExecutionContext

@Singleton
class AssetController @Inject()(actorSystem: ActorSystem)(implicit exec: ExecutionContext) extends Controller {
  def at(path: String, indexSuffix: String, file: String) = {
    // Append index suffix at the end of paths if terminating with / or empty
    val filePaths = if (file.endsWith("/") || file.isEmpty) file ++ indexSuffix else file

    controllers.Assets.at(path, filePaths.stripPrefix("/"))
  }
}
