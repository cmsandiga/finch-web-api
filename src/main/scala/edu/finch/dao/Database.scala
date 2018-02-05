package edu.finch.dao

import io.getquill.{NamingStrategy, SnakeCase}
import io.getquill.context.jdbc.JdbcContext
import io.getquill.context.sql.idiom.SqlIdiom
import shapeless.Nat._0


trait DatabaseComponent {

  val ctx :JdbcContext[SqlIdiom, SnakeCase]

}
