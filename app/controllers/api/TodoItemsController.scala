package controllers.api

import javax.inject._

import akka.actor.ActorSystem
import models.{TodoItem, TodoList}
import models.ModelJsonFormats._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext

@Singleton
class TodoItemsController @Inject()(actorSystem: ActorSystem)(implicit exec: ExecutionContext) extends Controller {

  private val todoListNotFoundResponse = NotFound(Json.obj(
    "message" -> "Specified list was not found."
  ))

  def create(todoListId: String) = Action { request =>
    val json = request.body.asJson

    TodoList.findById(todoListId).map { _ =>
      json.flatMap(v => (v \ "title").asOpt[String]).map { title =>
        val description = json.flatMap(v => (v \ "description").asOpt[String]).getOrElse("")
        val resultId = TodoItem.createWithAttributes(
          'todo_list_id -> todoListId,
          'title -> title,
          'description -> description
        )
        val resultRecord = TodoItem.findById(resultId).get

        Ok(Json.toJson(resultRecord))
      }.getOrElse(BadRequest("A todo-item must have a title."))
    }.getOrElse(todoListNotFoundResponse)
  }

  def getList(todoListId: String) = Action { _ =>
    TodoList.joins(TodoList.todoItemsRef).findById(todoListId).map { todoList =>
      val items = todoList.todoItems.getOrElse(Nil).map { item =>
        TodoItem.joins(TodoItem.tagsRef).findById(item.id).get
      }

      Ok(Json.toJson(items))
    }.getOrElse(todoListNotFoundResponse)
  }

}
