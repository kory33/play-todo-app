package models

import scalikejdbc.WrappedResultSet
import skinny.orm.{Alias, SkinnyCRUDMapper}

case class TodoItem(id: Long,
                    todoListId: String, todoList: Option[TodoList] = Option.empty,
                    title: String,
                    description: String)

object TodoItem extends SkinnyCRUDMapper[TodoItem] {
  override def defaultAlias: Alias[TodoItem] = createAlias("ti")

  override def extract(rs: WrappedResultSet, n: scalikejdbc.ResultName[TodoItem]): TodoItem = new TodoItem(
    id = rs.get(n.id),
    todoListId = rs.get(n.todoListId),
    title = rs.get(n.title),
    description = rs.get(n.description)
  )
}