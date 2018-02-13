package models

import scalikejdbc.WrappedResultSet
import skinny.orm.{Alias, SkinnyCRUDMapperWithId}

case class TodoList(id: String, title: String)

object TodoList extends SkinnyCRUDMapperWithId[String, TodoList] {
  override def generateId: String = java.util.UUID.randomUUID.toString

  override def idToRawValue(id: String): String = id
  override def rawValueToId(value: Any): String = value.toString

  override lazy val defaultAlias: Alias[TodoList] = createAlias("tl")

  override def extract(rs: WrappedResultSet, n: scalikejdbc.ResultName[TodoList]): TodoList = new TodoList(
    id    = rs.get(n.id),
    title = rs.get(n.title)
  )
}