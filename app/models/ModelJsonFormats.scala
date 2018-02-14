package models

import play.api.libs.json.{JsValue, Json, Writes}

object ModelJsonFormats {

  implicit val todoItemWrites: Writes[TodoItem] = new Writes[TodoItem] {
    override def writes(item: TodoItem): JsValue = Json.obj(
      "id" -> item.id,
      "title" -> item.title,
      "description" -> item.description
    ) ++ Option(item.tags).map { tags => Json.obj("tags" -> Json.toJson(tags)) }.getOrElse(Json.obj())
  }

  implicit val todoListWrites: Writes[TodoList] = new Writes[TodoList] {
    override def writes(list: TodoList): JsValue = Json.obj(
      "id" -> list.id,
      "title" -> list.title
    ) ++ Option(list.todoItems).map { togoItems => Json.obj("todo_items" -> Json.toJson(togoItems)) }.getOrElse(Json.obj())
  }

  implicit val tagWrites: Writes[Tag] = new Writes[Tag] {
    override def writes(tag: Tag): JsValue = Json.obj(
      "id" -> tag.id,
      "name" -> tag.name,
      "color" -> tag.color
    )
  }

}
