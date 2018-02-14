package models

import play.api.libs.json.{JsValue, Json, Writes}

object ModelJsonFormats {

  private def optionSeqToJson[T: Writes](attribute: String, seq: Option[Seq[T]]) = seq.map { s =>
    Json.obj(attribute -> Json.toJson(s))
  }.getOrElse(Json.obj())

  implicit val todoItemWrites: Writes[TodoItem] = new Writes[TodoItem] {
    override def writes(item: TodoItem): JsValue = Json.obj(
      "id" -> item.id,
      "title" -> item.title,
      "description" -> item.description
    ) ++ optionSeqToJson("tags", item.tags) ++ optionSeqToJson("replies", item.replies)
  }

  implicit val todoListWrites: Writes[TodoList] = new Writes[TodoList] {
    override def writes(list: TodoList): JsValue = Json.obj(
      "id" -> list.id,
      "title" -> list.title
    ) ++ optionSeqToJson("todo_items", list.todoItems) ++ optionSeqToJson("tags", list.tags)
  }

  implicit val tagWrites: Writes[Tag] = new Writes[Tag] {
    override def writes(tag: Tag): JsValue = Json.obj(
      "id" -> tag.id,
      "name" -> tag.name,
      "color" -> tag.color
    )
  }

  implicit val replyWrites: Writes[Reply] = new Writes[Reply] {
    override def writes(reply: Reply): JsValue = Json.obj(
      "id" -> reply.id,
      "content" -> reply.content
    )
  }

}
