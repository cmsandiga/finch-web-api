package edu.finch.resource

/**
  * @author <a href="mailto:csandiga@uolinc.com">Makoto Sandiga</a>
  * @since 30/01/18
  */

import edu.finch.model.Person
import edu.finch.service.PersonServiceComponent
import io.circe.generic.auto._
import io.finch.circe._
import io.finch.syntax.{get, post}
import io.finch.{Endpoint, Ok, _}

trait PersonResourceComponent {
  this: PersonServiceComponent =>

  class PersonResource {

    val personApi = findPersonApi :+: findPersonAllApi :+: postPersonApi

    def findPersonApi: Endpoint[Person] = get("person" :: path[Long]) { id: Long =>
      personService.findById(id) match {
        case Some(person) => Ok(person)
        case None => NotFound(PersonNotFound(id))
      }
    }

    def findPersonAllApi: Endpoint[List[Person]] = get("person") {
      Ok(personService.findAll())
    }

    def postPersonApi: Endpoint[Person] = post("person" :: jsonBody[SavePersonRequest]) { (request: SavePersonRequest) =>
      val person = Person(0L, request.name, request.age)
      personService.insert(person)
      Created(person)
    }

    case class PersonNotFound(id: Long) extends Exception {
      override def getMessage: String = s"Person(${id.toString}) not found."
    }

  }

}