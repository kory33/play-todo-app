package models

import play.api.libs.json.{JsValue, Json, Writes}

object ModelJsonFormats {

  implicit val todoItemWrites: Writes[TodoItem] = new Writes[TodoItem]() {
    override def writes(item: TodoItem): JsValue = Json.obj(
      "id" -> item.id,
      "title" -> item.title,
      "description" -> item.description
    ) ++ item.todoList.map { list => Json.obj("todo_list" -> Json.toJson(list)) }.getOrElse(Json.obj())
  }

  implicit val todoListWrites: Writes[TodoList] = new Writes[TodoList] {
    override def writes(list: TodoList): JsValue = Json.obj(
      "id" -> list.id,
      "title" -> list.title,
      "seq" -> Json.toJson(list.todoItems)
    )
  }

}
