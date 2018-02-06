name := "finch-test"

version := "0.1"

scalaVersion := "2.12.4"

lazy val finchVersion = "0.16.0"
lazy val circeVersion = "0.9.0"
lazy val mysqlVersion = "5.1.38"
lazy val quillVersion = "2.3.2"
lazy val configTypeSafeVersion = "1.3.1"
lazy val scalaTestVersion = "3.0.4"

libraryDependencies ++= Seq(
  "com.github.finagle" %% "finch-core" % finchVersion,
  "com.github.finagle" %% "finch-circe" % finchVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "mysql" % "mysql-connector-java" % mysqlVersion,
  "io.getquill" %% "quill-jdbc" % quillVersion,
  "com.typesafe" % "config" % configTypeSafeVersion
)

libraryDependencies ++= Seq(
  "org.scalactic" %% "scalactic" % scalaTestVersion,
  "com.h2database" % "h2" % "1.4.192" % "test",
  "org.scalatest" %% "scalatest" % scalaTestVersion % "test",

)

