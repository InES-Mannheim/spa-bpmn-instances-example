package dal

import java.util.UUID
import javax.inject.{Inject, Singleton}

import org.hashids.Hashids
import rdf.{IriImplicits, ProcessVocabulary}
import IriImplicits._
import info.aduna.iteration.Iterations
import org.openrdf.model._
import org.openrdf.model.impl.LinkedHashModel
import org.openrdf.model.vocabulary.RDF
import org.openrdf.repository.Repository
import play.api.Logger
import play.api.inject.ApplicationLifecycle
import rdf.ProcessVocabulary

import scala.concurrent.Future
import scala.util.{Random, Try}

@Singleton
class ProcessRepository @Inject()(lifeCycle: ApplicationLifecycle, val repo: Repository) extends ProcessVocabulary with Repositories{

  lifeCycle.addStopHook {
    () => Future.successful(repo.shutDown())
  }
  val pathToResources: List[String] = List("resources/BPMN_2.0_ontology.owl", "resources/scanMailProcessModel.owl")
  val iris: List[IRI] = List("http://spa.de/bpmn2/model", "http://localhost:9000/processes/scanMail")
  loadFixtures(pathToResources, iris)
  defineScanMailAsProcess(iris(1))

  tryWithConnection { connection =>
    Logger.info(f"Statements in the repo: ${Iterations.asList(connection.getStatements(null, null, null, true))}%.400s...")
  }

  def defineScanMailAsProcess(iri:IRI) = defineIriAsProcess(iri, "http://localhost:9000/processes/scanMail")

  def findAll: Model = tryWithConnection { connection =>
    Iterations.asList(connection.getStatements(null, RDF.TYPE, Process)) } map { statements =>
    new LinkedHashModel(statements)
  } getOrElse new LinkedHashModel

  def findById(id: String): Model = tryWithConnection { connection =>
    val idUri: IRI = s"http://localhost:9000/processes/$id"
    Logger.info(s"Loading graph for process $idUri")
    Iterations.asList(connection.getStatements(null, null, null, true, idUri))
  } map { statements =>
    new LinkedHashModel(statements)
  } getOrElse new LinkedHashModel

  def create(model: Model):Try[IRI]= tryWithConnection { connection =>
    val id: IRI = s"http://localhost:9000/processes/${generateId()}"
    connection.add(model, id)
    defineIriAsProcess(id, id)
    id
  }

  private def generateId():String = Hashids("salty") encode scala.math.abs(Random.nextLong())

  def defineIriAsProcess(iri: IRI, context:IRI = "http://localhost:9000"): Unit = tryWithConnection(connection => connection.add(iri, RDF.TYPE, Process, context))
}

