package controllers.api

import javax.inject._

import akka.actor.ActorSystem
import play.api.mvc._

import scala.concurrent.ExecutionContext

@Singleton
class TodoListsController @Inject()(actorSystem: ActorSystem)(implicit exec: ExecutionContext) extends Controller  {

  def createTodoList = Action { request =>
    Ok("Processed request") // TODO return structured JSON for Todo-List
  }

}
