package controllers.api

import javax.inject._

import akka.actor.ActorSystem
import models.TodoList
import models.ModelJsonFormats._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext

import utils.OptionUtil._

@Singleton
class TodoListsController @Inject()(actorSystem: ActorSystem)(implicit exec: ExecutionContext) extends Controller  {

  private val notFoundResponse = NotFound(Json.obj(
    "message" -> "Resource not found."
  ))

  /**
    * Creates a record of a TodoList.
    */
  def create = Action { request =>
    val listTitle = request.body.asJson
        .flatMap{ jsonObj => (jsonObj \ "title").asOpt[String] }
        .flatMap{ v => if(v.isEmpty || v.length > 60) None else Option(v) }
        .getOrElse{ "Untitled Todo List" }

    Ok(Json.toJson(TodoList.create(listTitle)))
  }

  def renameTitle(id: String) = Action { request =>
    val newTitle = request.body.asJson
      .flatMap { jsonObj => (jsonObj \ "title").asOpt[String] }

    TodoList.updateTitle(id, newTitle)
      .projectLeftWith(notFoundResponse)
      .map { updated => Ok(Json.toJson(updated)) }
      .merge
  }

  /**
    * Reads a record of a TodoList specified by id.
    */
  def read(id: String) = Action {
    TodoList.joins(TodoList.todoItemsRef).joins(TodoList.tagsRef).findById(id)
      .projectLeftWith(notFoundResponse)
      .map { todoList => Ok(Json.toJson(todoList)) }
      .merge
  }

  def delete(id: String) = Action {
    val deleteCount = TodoList.deleteById(id)
    if (deleteCount != 0) Ok(Json.obj()) else notFoundResponse
  }

}
