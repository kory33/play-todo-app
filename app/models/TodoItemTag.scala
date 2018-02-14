package models

import skinny.orm.{Alias, SkinnyJoinTable}

case class TodoItemTag(todoItemId: Long, tagId: Long)

object TodoItemTag extends SkinnyJoinTable[TodoItemTag] {
  override def defaultAlias: Alias[TodoItemTag] = createAlias("titag")
}
