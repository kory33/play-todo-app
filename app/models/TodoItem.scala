package models

import scalikejdbc.WrappedResultSet
import skinny.orm.feature.associations.HasManyAssociation
import skinny.orm.{Alias, SkinnyCRUDMapper}

case class TodoItem(id: Long,
                    todoListId: String,
                    title: String,
                    description: String,
                    tags: Option[Seq[Tag]] = None,
                    replies: Option[Seq[Reply]] = None)

object TodoItem extends SkinnyCRUDMapper[TodoItem] {
  override def defaultAlias: Alias[TodoItem] = createAlias("ti")

  def createWith(todoListId: String, title: String, description: Option[String]): TodoItem = {
    val resultId = createWithAttributes(
      'todo_list_id -> todoListId,
      'title -> title,
      'description -> description.getOrElse("")
    )

    findById(resultId).get
  }

  override def extract(rs: WrappedResultSet, n: scalikejdbc.ResultName[TodoItem]): TodoItem = new TodoItem(
    id = rs.get(n.id),
    todoListId = rs.get(n.todoListId),
    title = rs.get(n.title),
    description = rs.get(n.description)
  )

  lazy val tagsRef: HasManyAssociation[TodoItem] = hasManyThrough[Tag](
    through = TodoItemTag,
    many = Tag,
    merge = (todoItem, tags) => todoItem.copy(tags = Option(tags))
  )

  lazy val replyRef: HasManyAssociation[TodoItem] = hasMany[Reply](
    many = Reply -> Reply.defaultAlias,
    on = (item, reply) => scalikejdbc.sqls.eq(item.id, reply.todoItemId),
    merge = (item, replies) => item.copy(replies = Option(replies))
  )

}
