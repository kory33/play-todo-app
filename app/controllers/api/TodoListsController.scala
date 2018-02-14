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

  private val notFoundResponse = NotFound(Json.obj(
    "message" -> "Resource not found."
  ))

  /**
    * Creates a record of a TodoList.
    */
  def create = Action { request =>
    val json = request.body.asJson

    // TODO Validate title size
    val listTitle = json.flatMap{ v => (v \ "title").asOpt[String] }
      .getOrElse{ "Untitled Todo List" }

    val recordId = TodoList.createWithAttributes('title -> listTitle)
    val resultRecord = TodoList.findById(recordId).get
    Ok(Json.toJson(resultRecord))
  }

  /**
    * Reads a record of a TodoList specified by id.
    */
  def read(id: String) = Action {
    TodoList.joins(TodoList.todoItemsRef).joins(TodoList.tagsRef).findById(id).map { todoList =>
      Ok(Json.toJson(todoList))
    }.getOrElse(
      notFoundResponse
    )
  }

}
