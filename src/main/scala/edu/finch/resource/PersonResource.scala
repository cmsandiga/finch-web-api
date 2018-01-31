package edu.finch.resource

/**
  * @author <a href="mailto:csandiga@uolinc.com">Makoto Sandiga</a>
  * @since 30/01/18
  */

import edu.finch.model.Person
import io.finch.{Endpoint, Ok}
import io.finch.syntax.get

object PersonResource {
  def api: Endpoint[Person] = get("hello") {
    Ok(Person("Cristain", 23))
  }
}
