package models

import scalikejdbc.WrappedResultSet
import skinny.orm.{Alias, SkinnyCRUDMapper}

case class Tag(id: Long,
               name: String,
               color: String)

object Tag extends SkinnyCRUDMapper[Tag] {
  override def defaultAlias: Alias[Tag] = createAlias("tag")

  override def extract(rs: WrappedResultSet, n: scalikejdbc.ResultName[Tag]): Tag = new Tag(
    id = rs.get(n.id),
    name = rs.get(n.name),
    color = rs.get(n.color)
  )
}