package controllers.api

import javax.inject.{Inject, Singleton}

import akka.actor.ActorSystem
import models.{Tag, TodoItem, TodoItemTag, TodoList}
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

import scala.concurrent.ExecutionContext
import models.ModelJsonFormats._

@Singleton
class TagController @Inject()(actorSystem: ActorSystem)(implicit exec: ExecutionContext) extends Controller {

  private val todoListNotFoundResponse = NotFound(Json.obj(
    "message" -> "Specified list was not found."
  ))

  private val todoItemNotFoundResponse = NotFound(Json.obj(
    "message" -> "Specified item was not found."
  ))

  private val defaultTagColor = "777777"

  def create(todoListId: String) = Action { request =>
    val json = request.body.asJson

    TodoList.findById(todoListId).map { _ =>
      json.flatMap { v => (v \ "name").asOpt[String] }.map { tagName =>
        // TODO validate this
        val tagColor = json.flatMap { v => (v \ "color").asOpt[String] }.getOrElse(defaultTagColor)

        val resultId = Tag.createWithAttributes('name -> tagName, 'todoListId -> todoListId, 'color -> tagColor)
        val resultRecord = Tag.findById(resultId).get
        Ok(Json.toJson(resultRecord))
      }.getOrElse(BadRequest(Json.obj("message" -> "Tag name is required.")))
    }.getOrElse(todoListNotFoundResponse)
  }

}
