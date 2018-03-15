package models

import scalikejdbc.WrappedResultSet
import skinny.orm.feature.associations.HasManyAssociation
import skinny.orm.{Alias, SkinnyCRUDMapperWithId}

case class TodoList(id: String, title: String, todoItems: Option[Seq[TodoItem]] = None, tags: Option[Seq[Tag]] = None)

object TodoList extends SkinnyCRUDMapperWithId[String, TodoList] {
  override def useExternalIdGenerator = true
  override def generateId: String = java.util.UUID.randomUUID.toString
  override def idToRawValue(id: String): String = id
  override def rawValueToId(value: Any): String = value.toString

  override lazy val defaultAlias: Alias[TodoList] = createAlias("tl")

  def updateTitle(id: String, optionalTitle: Option[String]): Option[TodoList] = {
    findById(id).map { found =>
      val title = optionalTitle.getOrElse(found.title)
      updateById(found.id).withAttributes('title -> title)
      findById(id).get
    }
  }

  lazy val todoItemsRef: HasManyAssociation[TodoList] = hasMany[TodoItem](
    many = TodoItem -> TodoItem.defaultAlias,
    on = (list, item) => scalikejdbc.sqls.eq(list.id, item.todoListId),
    merge = (list, items) => list.copy(todoItems = Option(items))
  )

  lazy val tagsRef: HasManyAssociation[TodoList] = hasMany[Tag](
    many = Tag -> Tag.defaultAlias,
    on = (list, tag) => scalikejdbc.sqls.eq(list.id, tag.todoListId),
    merge = (list, tags) => list.copy(tags = Option(tags))
  )

  override def extract(rs: WrappedResultSet, n: scalikejdbc.ResultName[TodoList]): TodoList = new TodoList(
    id    = rs.get(n.id),
    title = rs.get(n.title)
  )
}