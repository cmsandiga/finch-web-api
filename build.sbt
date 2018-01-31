name := "finch-test"

version := "0.1"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "com.github.finagle" %% "finch-core" % "0.16.0",
  "com.github.finagle" %% "finch-circe" % "0.16.0",
  "io.circe" %% "circe-generic" % "0.9.0"
)