package models

import play.api.libs.json.{JsValue, Json, Writes}
import scalikejdbc.WrappedResultSet
import skinny.orm.feature.associations.HasManyAssociation
import skinny.orm.{Alias, SkinnyCRUDMapperWithId}

case class TodoList(id: String, title: String, todoItems: Seq[TodoItem] = Nil)

object TodoList extends SkinnyCRUDMapperWithId[String, TodoList] {
  override def useExternalIdGenerator = true
  override def generateId: String = java.util.UUID.randomUUID.toString
  override def idToRawValue(id: String): String = id
  override def rawValueToId(value: Any): String = value.toString

  override lazy val defaultAlias: Alias[TodoList] = createAlias("tl")

  implicit val todoListWrites: Writes[TodoList] = new Writes[TodoList] {
    override def writes(list: TodoList): JsValue = Json.obj(
      "id" -> list.id,
      "title" -> list.title,
      "seq" -> Json.toJson(list.todoItems)
    )
  }

  lazy val todoItemsRef: HasManyAssociation[TodoList] = hasMany[TodoItem](
    many = TodoItem -> TodoItem.defaultAlias,
    on = (list, item) => scalikejdbc.sqls.eq(list.id, item.id),
    merge = (list, items) => list.copy(todoItems = items)
  )

  override def extract(rs: WrappedResultSet, n: scalikejdbc.ResultName[TodoList]): TodoList = new TodoList(
    id    = rs.get(n.id),
    title = rs.get(n.title)
  )
}