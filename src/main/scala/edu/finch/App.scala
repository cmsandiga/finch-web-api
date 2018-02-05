package edu.finch

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.{Http, Service}
import com.twitter.util.Await
import edu.finch.dao.{DatabaseComponent, PersonDAOComponent}
import edu.finch.resource.PersonResourceComponent
import edu.finch.service.PersonServiceComponent
import io.circe.generic.auto._
import io.finch.Application
import io.finch.circe._
import io.getquill.context.jdbc.JdbcContext
import io.getquill.context.sql.idiom.SqlIdiom
import io.getquill.{MysqlJdbcContext, SnakeCase}

/**
  * @author <a href="mailto:csandiga@uolinc.com">Makoto Sandiga</a>
  * @since 30/01/18
  */
object App extends App {

  val port = ":8080"

  val api: Service[Request, Response] = ComponentRegistry.personResource.personApi.toServiceAs[Application.Json]

  val server = Http.server.serve(port, api)

  Await.ready(server)

  object ComponentRegistry extends
    PersonResourceComponent with
    PersonServiceComponent with
    PersonDAOComponent with
    DatabaseComponent {

    val personResource = new PersonResource
    val personService = new PersonService
    val personDAO = new PersonDAO
    val context = new MysqlJdbcContext[SnakeCase](SnakeCase, "ctx")
    override val ctx: JdbcContext[SqlIdiom, SnakeCase] = context.asInstanceOf[JdbcContext[SqlIdiom, SnakeCase]]
  }

}


