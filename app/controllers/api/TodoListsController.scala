package controllers.api

import javax.inject._

import akka.actor.ActorSystem
import models.TodoList
import models.ModelJsonFormats._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext

@Singleton
class TodoListsController @Inject()(actorSystem: ActorSystem)(implicit exec: ExecutionContext) extends Controller  {

  val notFoundResponse = NotFound(Json.obj(
    "message" -> "Resource not found."
  ))

  def create = Action { request =>
    val json = request.body.asJson

    // TODO Validate title size
    val listTitle = json.flatMap{ v => (v \ "title").asOpt[String] }
      .getOrElse{ "Untitled Todo List" }

    val recordId = TodoList.createWithAttributes('title -> listTitle)
    val resultRecord = TodoList.joins(TodoList.todoItemsRef).findById(recordId).get
    Ok(Json.toJson(resultRecord))
  }

  def read(id: String) = Action {
    TodoList.joins(TodoList.todoItemsRef).findById(id).map { todoList =>
      Ok(Json.toJson(todoList))
    }.getOrElse(
      notFoundResponse
    )
  }

}
