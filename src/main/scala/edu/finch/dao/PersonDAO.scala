package edu.finch.dao

import edu.finch.model.Person

trait PersonDAOComponent { this:DatabaseComponent =>
  import ctx._

  val personDAO: PersonDAO

  class PersonDAO {

    def insert(person: Person): Unit = {
      val q = quote {
        query[Person].insert(lift(person))
      }
      ctx.run(q)
    }

    def findById(id: Long): Option[Person] = {
      val q = quote {
        query[Person].filter(_.id == lift(id))
      }
      ctx.run(q).headOption
    }

    def findAll(): List[Person] = {
      val q = quote {
        query[Person]
      }
      ctx.run(q)
    }
  }
}