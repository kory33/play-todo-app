package models

import play.api.libs.json.{JsValue, Json, Writes}
import scalikejdbc.WrappedResultSet
import skinny.orm.{Alias, SkinnyCRUDMapper}

case class TodoItem(id: Long,
                    todoListId: Long, todoList: Option[TodoList] = Option.empty,
                    title: String,
                    description: String)

object TodoItem extends SkinnyCRUDMapper[TodoItem] {
  override def defaultAlias: Alias[TodoItem] = createAlias("ti")

  implicit val todoItemWrites: Writes[TodoItem] = new Writes[TodoItem]() {
    override def writes(item: TodoItem): JsValue = Json.obj(
      "id" -> item.id,
      "title" -> item.title,
      "description" -> item.description
    ) ++ item.todoList.map { list => Json.obj("todo_list" -> Json.toJson(list)) }.getOrElse(Json.obj())
  }

  hasOne[TodoList](
    TodoList,
    (todoItem, todoList) => todoItem.copy(todoList = todoList)
  ).byDefault

  override def extract(rs: WrappedResultSet, n: scalikejdbc.ResultName[TodoItem]): TodoItem = new TodoItem(
    id = rs.get(n.id),
    todoListId = rs.get(n.todoListId),
    title = rs.get(n.title),
    description = rs.get(n.description)
  )
}