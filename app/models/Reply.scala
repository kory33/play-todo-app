package models

import scalikejdbc.WrappedResultSet
import skinny.orm.{Alias, SkinnyCRUDMapper}

case class Reply(id: Long, todoItemId: Long, content: String)

object Reply extends SkinnyCRUDMapper[Reply] {
  override def defaultAlias: Alias[Reply] = createAlias("rp")

  override def extract(rs: WrappedResultSet, n: scalikejdbc.ResultName[Reply]): Reply = new Reply(
    id = rs.get(n.id),
    todoItemId = rs.get(n.todoItemId),
    content = rs.get(n.content)
  )
}
