package edu.finch.service

import edu.finch.dao.PersonDAOComponent
import edu.finch.model.Person

trait PersonServiceComponent {
  this: PersonDAOComponent =>

  val personService: PersonService

  class PersonService {

    val personDAO = new PersonDAO()

    def insert(person: Person): Unit = personDAO.insert(person)

    def findById(id: Long): Option[Person] = personDAO.findById(id)

    def findAll(): List[Person] = personDAO.findAll()
  }

}

