package edu.finch.resource

/**
  * @author <a href="mailto:csandiga@uolinc.com">Makoto Sandiga</a>
  * @since 30/01/18
  */

import edu.finch.model.Person
import io.circe.generic.auto._
import io.finch.circe._
import io.finch.syntax.{get, post}
import io.finch.{Endpoint, Ok, _}
import io.getquill._

import scala.collection.mutable

object PersonResource {

  lazy val ctx = new MysqlJdbcContext[SnakeCase](SnakeCase, "ctx")

  import ctx._

  val persons = mutable.Map[Long, Person]()

  def getApi: Endpoint[Person] = get("person" :: path[Long]) { id: Long =>

    persons.get(id) match {
      case Some(person) => Ok(person)
      case None => NotFound(PersonNotFound(id))
    }
  }

  def posted: Endpoint[Person] = jsonBody[Person]

  def postApi: Endpoint[Person] = post("person" :: posted) { (person: Person) =>
    persons(person.id) = person

    val q = quote {
      query[Person].insert(person)
    }

    val result = ctx.run(query[Person])

    Created(person)
  }

  case class PersonNotFound(id: Long) extends Exception {
    override def getMessage: String = s"Person(${id.toString}) not found."
  }

}
