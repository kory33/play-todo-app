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

  private val todoItemLacksTitleResponse = BadRequest(Json.obj(
    "message" -> "A todo-item must have a non-empty title."
  ))

  def create(todoListId: String) = Action { request =>
    val json = request.body.asJson

    TodoList.findById(todoListId)
      .toLeft(todoListNotFoundResponse).left.map { _ =>
      json
        .flatMap { v => (v \ "title").asOpt[String] }
        .flatMap { title => if (title.isEmpty) None else Option(title) }
        .map { title =>
          val description = json.flatMap(v => (v \ "description").asOpt[String]).getOrElse("")
          val resultId = TodoItem.createWithAttributes(
            'todo_list_id -> todoListId,
            'title -> title,
            'description -> description
          )
          val resultRecord = TodoItem.findById(resultId).get

          Ok(Json.toJson(resultRecord))
        }
        .getOrElse(todoItemLacksTitleResponse)
    }.merge
  }

  def getList(todoListId: String) = Action { _ =>
    TodoList.findById(todoListId).map { _ =>
      val items = TodoItem.joins(TodoItem.tagsRef).findAllBy(
        scalikejdbc.sqls.eq(TodoItem.defaultAlias.todoListId, todoListId)
      )

      Ok(Json.toJson(items))
    }.getOrElse(todoListNotFoundResponse)
  }

}
