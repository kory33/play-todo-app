name := """play-todo-app"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,

  "mysql" % "mysql-connector-java" % "5.1.41",
  "org.postgresql" % "postgresql" % "9.4-1201-jdbc41",

  "org.skinny-framework" %% "skinny-orm"      % "2.5.2",
  "ch.qos.logback"       %  "logback-classic" % "1.1.+"
)

initialCommands := """
import scalikejdbc._
import skinny.orm._, feature._
import org.joda.time._
skinny.DBSettings.initialize()
skinny.dbmigration.DBMigration.migrate()

implicit val session = AutoSession
"""