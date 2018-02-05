package edu.finch

import java.nio.charset.StandardCharsets

import com.twitter.finagle.http.Status
import edu.finch.dao.{DatabaseComponent, PersonDAOComponent}
import edu.finch.resource.{PersonResourceComponent, SavePersonRequest}
import edu.finch.service.PersonServiceComponent
import io.circe.generic.auto._
import io.finch.circe._
import io.finch.{Application, Input}
import io.getquill.{H2JdbcContext, SnakeCase}
import io.getquill.context.jdbc.JdbcContext
import io.getquill.context.sql.idiom.SqlIdiom
import org.scalatest.{FlatSpec, Matchers}

class PersonResourceSpec extends FlatSpec with Matchers {

  object ComponentRegistry extends
    PersonResourceComponent with
    PersonServiceComponent with
    PersonDAOComponent with
    DatabaseComponent {

    val personResource = new PersonResource
    val personService = new PersonService
    val personDAO = new PersonDAO
    val context = new H2JdbcContext[SnakeCase](SnakeCase, "ctx")
    override val ctx: JdbcContext[SqlIdiom, SnakeCase] = context.asInstanceOf[JdbcContext[SqlIdiom, SnakeCase]]
  }

  import ComponentRegistry.personResource._

  behavior of "Get Person"

  it should "get person by id" in {
    val input = Input.get("/person/0")
    val response = findPersonApi(input)

    response.awaitOutputUnsafe().map(_.status) shouldBe Some(Status.NotFound)
    a[PersonNotFound] shouldBe thrownBy(response.awaitValueUnsafe())
  }

  behavior of "Post Person"

  it should "post person" in {
    val request = SavePersonRequest(name = "Makoto", age = 23)
    val input = Input.post("/person").withBody[Application.Json](request, Some(StandardCharsets.UTF_8))
    val response = postPersonApi(input)
    response.awaitOutputUnsafe().map(_.status) shouldBe Some(Status.Created)

    val Some(responseValue) = response.awaitValueUnsafe()

    responseValue.name shouldBe request.name
    responseValue.age shouldBe request.age

  }
}
