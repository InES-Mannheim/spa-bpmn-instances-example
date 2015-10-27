package dal

import javax.inject.{Inject, Singleton}

import info.aduna.iteration.Iterations
import org.openrdf.model.impl.{LinkedHashModel, SimpleValueFactory}
import org.openrdf.model.vocabulary.RDF
import org.openrdf.model.{Model, IRI, Statement, ValueFactory}
import org.openrdf.repository.sail.SailRepository
import org.openrdf.repository.{Repository, RepositoryConnection, RepositoryResult}
import org.openrdf.rio.RDFFormat
import org.openrdf.sail.memory.MemoryStore
import play.api.Play
import play.api.Play.current
import play.api.inject.ApplicationLifecycle

import scala.concurrent.Future
import scala.util.Try

trait VocabularyDependencies extends ProcessVocabulary

@Singleton
class ProcessRepository @Inject()(lifeCycle: ApplicationLifecycle, vf: ValueFactory, repo: Repository) extends VocabularyDependencies {

  lifeCycle.addStopHook {
    () => Future.successful(repo.shutDown())
  }

  val pathToResources: List[String] = List("resources/BPMN_2.0_ontology.owl", "resources/scanMailProcessModel.owl")
  val iris: List[IRI] = List(vf.createIRI("http://spa.de/bpmn2/model"), vf.createIRI("http://localhost:9000/processes/scanMail"))
  loadFixtures(pathToResources, iris)
  defineScanMailAsProcess(iris(1))

  def loadFixtures(pathToResources: List[String], iris: List[IRI]): Unit =
    for {
      (path, iri) <- pathToResources zip iris
    } yield {
      Play.resourceAsStream(path).foreach { in =>
          tryWithConnection(connection => connection.add(in, null, RDFFormat.RDFXML, iri))
      }
    }

  def defineScanMailAsProcess(iri: IRI): Unit = tryWithConnection(connection => connection.add(iri, RDF.TYPE, Process))

  def findAll: Model = tryWithConnection { connection =>
    connection.getStatements(null, RDF.TYPE, Process) } map { statements =>
    new LinkedHashModel(Iterations.asList(statements))
  } getOrElse new LinkedHashModel

  private def tryWithConnection[T](consumer: RepositoryConnection => T): Try[T] = Try(repo.getConnection).flatMap { connection =>
    Try(consumer(connection))
  }

}

