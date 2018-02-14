package models

import scalikejdbc.WrappedResultSet
import skinny.orm.feature.associations.HasManyAssociation
import skinny.orm.{Alias, SkinnyCRUDMapper}

case class TodoItem(id: Long,
                    todoListId: String,
                    title: String,
                    description: String,
                    tags: Seq[Tag] = Nil)

object TodoItem extends SkinnyCRUDMapper[TodoItem] {
  override def defaultAlias: Alias[TodoItem] = createAlias("ti")

  override def extract(rs: WrappedResultSet, n: scalikejdbc.ResultName[TodoItem]): TodoItem = new TodoItem(
    id = rs.get(n.id),
    todoListId = rs.get(n.todoListId),
    title = rs.get(n.title),
    description = rs.get(n.description)
  )

  lazy val tagsRef: HasManyAssociation[TodoItem] = hasManyThrough[Tag](
    through = TodoItemTag,
    many = Tag,
    merge = (todoItem, tags) => todoItem.copy(tags = tags)
  )
}
