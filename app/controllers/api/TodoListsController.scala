package controllers.api

import javax.inject._

import akka.actor.ActorSystem
import models.TodoList
import play.api.mvc._

import scala.concurrent.ExecutionContext

@Singleton
class TodoListsController @Inject()(actorSystem: ActorSystem)(implicit exec: ExecutionContext) extends Controller  {

  def createTodoList = Action { request =>
    val json = request.body.asJson

    val listTitle = json.flatMap{ v => (v \ "title").asOpt[String] }
      .getOrElse{ "Untitled Todo List" }

    val recordId = TodoList.createWithAttributes('title -> listTitle)

    Ok(s"created TodoList object with id: $recordId")
  }

}
