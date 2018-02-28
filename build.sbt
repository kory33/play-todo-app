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

unmanagedResourceDirectories in Assets <+= (baseDirectory / "frontend/build")


fork in run := true