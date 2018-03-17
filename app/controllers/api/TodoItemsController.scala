package controllers.api

import javax.inject._

import akka.actor.ActorSystem
import models.{TodoItem, TodoList}
import models.ModelJsonFormats._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext

import utils.EitherUtil._
import utils.JsonUtil._

@Singleton
class TodoItemsController @Inject()(actorSystem: ActorSystem)(implicit exec: ExecutionContext) extends Controller {

  private val todoListNotFoundResponse = NotFound(Json.obj(
    "message" -> "Specified list was not found."
  ))

  private val todoItemNotFoundResponse = NotFound(Json.obj(
    "message" -> "Specified todo-item was not found."
  ))

  private val todoItemLacksTitleResponse = BadRequest(Json.obj(
    "message" -> "A todo-item must have a non-empty title."
  ))

  def create(todoListId: String) = Action { request =>
    val json = request.body.asJson
    val itemTitle = json.getStringAt("title")
      .flatMap { title => if (title.isEmpty) None else Option(title) }

    TodoList.findById(todoListId).toLeft(todoListNotFoundResponse)
      .flatMapLeft { _ => itemTitle.toLeft(todoItemLacksTitleResponse) }
      .mapLeft { title =>
        val result = TodoItem.createWith(todoListId, title, json.getStringAt("description"))
        Ok(Json.toJson(result))
      }
      .merge
  }

  private def getItemsOn(todoListId: String) = TodoList.findById(todoListId)
    .map { _ =>
      TodoItem.joins(TodoItem.tagsRef).findAllBy(
        scalikejdbc.sqls.eq(TodoItem.defaultAlias.todoListId, todoListId.toLong)
      )
    }

  def getList(todoListId: String) = Action {
    getItemsOn(todoListId).toLeft(todoListNotFoundResponse)
      .mapLeft { searchResult => Ok(Json.toJson(searchResult)) }
      .merge
  }

  def delete(todoListId: String, todoItemId: String) = Action { _ =>
    getItemsOn(todoListId).toLeft(todoListNotFoundResponse)
      .flatMapLeft { searchResult =>
        searchResult
          .find { item => item.id.toString.equals(todoItemId) }
          .toLeft(todoItemNotFoundResponse)
      }
      .mapLeft { foundItem =>
        TodoItem.deleteById(foundItem.id)
        Ok(Json())
      }.merge
  }

}
