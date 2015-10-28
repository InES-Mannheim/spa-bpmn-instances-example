package dal

import org.openrdf.model.IRI
import org.openrdf.repository.{Repository, RepositoryConnection}
import org.openrdf.rio.RDFFormat
import play.api.{Logger, Play}
import play.api.Play.current

import scala.util.{Try, Success, Failure}

trait Repositories {

  val repo:Repository

  def loadFixtures(pathToResources: List[String], iris: List[IRI]): Unit =
    for {
      (path, iri) <- pathToResources zip iris
    } yield {
      Play.resourceAsStream(path).foreach { in =>
        tryWithConnection(connection => connection.add(in, "http://localhost:9000", RDFFormat.RDFXML, iri)) match {
          case Failure(ex) => Logger.warn(s"Could not load fixtures for IRI '$iri':", ex)
          case Success(_) => Logger.info(s"Successfully loaded fixtures for IRI '$iri'")
        }
      }
    }

  def tryWithConnection[T](consumer: RepositoryConnection => T): Try[T] = Try(repo.getConnection) flatMap { connection =>
    val result = Try(consumer(connection))
    connection.close()
    result
  }
}
