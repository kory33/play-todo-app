package models

import play.api.libs.json.{JsValue, Json, Writes}

object ModelJsonFormats {

  private def nullableSeqToJson[T](nullableSeq: Seq[T], attributeName: String) = Option(nullableSeq).map {
    seq => Json.obj(attributeName -> Json.toJson(seq))
  }.getOrElse(Json.obj())

  implicit val todoItemWrites: Writes[TodoItem] = new Writes[TodoItem] {
    override def writes(item: TodoItem): JsValue = Json.obj(
      "id" -> item.id,
      "title" -> item.title,
      "description" -> item.description
    ) ++ nullableSeqToJson(item.tags, "tags")
  }

  implicit val todoListWrites: Writes[TodoList] = new Writes[TodoList] {
    override def writes(list: TodoList): JsValue = Json.obj(
      "id" -> list.id,
      "title" -> list.title
    ) ++ nullableSeqToJson(list.todoItems, "todo_items") ++ nullableSeqToJson(list.tags, "tags")
  }

  implicit val tagWrites: Writes[Tag] = new Writes[Tag] {
    override def writes(tag: Tag): JsValue = Json.obj(
      "id" -> tag.id,
      "name" -> tag.name,
      "color" -> tag.color
    )
  }

}
