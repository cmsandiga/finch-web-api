package edu.finch

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.{Http, Service}
import com.twitter.util.Await
import edu.finch.resource.PersonResource
import io.circe.generic.auto._
import io.finch.Application
import io.finch.circe._

/**
  * @author <a href="mailto:csandiga@uolinc.com">Makoto Sandiga</a>
  * @since 30/01/18
  */
object App extends App {

  val personApi: Service[Request, Response] = PersonResource.api.toServiceAs[Application.Json]

  val server = Http.server.serve(":8080", personApi)

  Await.ready(server)
}
