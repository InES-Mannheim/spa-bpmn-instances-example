package dal

import javax.inject.{Inject, Singleton}

import rdf.{IriImplicits, ProcessInstanceVocabulary}
import IriImplicits._
import info.aduna.iteration.Iterations
import org.openrdf.model._
import org.openrdf.model.impl.LinkedHashModel
import org.openrdf.model.vocabulary.RDF
import org.openrdf.repository.Repository
import play.api.Logger
import play.api.inject.ApplicationLifecycle
import rdf.ProcessInstanceVocabulary

import scala.concurrent.Future
import scala.util.Success

@Singleton
class ProcessInstanceRepository @Inject()(lifeCycle: ApplicationLifecycle, val repo: Repository) extends ProcessInstanceVocabulary with Repositories {

  lifeCycle.addStopHook {
    () => Future.successful(repo.shutDown())
  }
  val pathToResources: List[String] = List("resources/instances.owl", "resources/scannedAsSpamInstance.owl")
  val iris: List[IRI] = List("http://spa.de/bpmn2/instances", "http://localhost:9000/processes/scanMail/instances/1")
  loadFixtures(pathToResources, iris)
  defineScannedAsMailAsProcessInstanceOfScanMailProcess(iris(1))

  tryWithConnection { connection =>
    Logger.info(f"Statements in the repo: ${Iterations.asList(connection.getStatements(null, null, null, true))}%.400s...")
  }

  def defineScannedAsMailAsProcessInstanceOfScanMailProcess(iri: IRI) = tryWithConnection {connection =>
    connection.add(iri,RDF.TYPE,ProcessInstance)
    connection.add(iri,isInstanceOfProcess,"http://localhost:9000/processes/scanMail")
  }

  def findAllInstancesForProcess(processId: String): Model = tryWithConnection { connection =>
    val idUri: IRI = s"http://localhost:9000/processes/$processId"
    Logger.info(s"Loading all instances for process $idUri")
    Iterations.asList(connection.getStatements(null, isInstanceOfProcess, idUri, true))
  } map { statements => new LinkedHashModel(statements)
  } getOrElse new LinkedHashModel

  def findInstanceForProcess(processId:String, instanceId:String) = tryWithConnection{connection =>
    val idUri: IRI = s"http://localhost:9000/processes/$processId/instances/$instanceId"
    Logger.info(s"Loading instance $idUri")
    Iterations.asList(connection.getStatements(null, null, null, true, idUri))
  } map { statements =>
    new LinkedHashModel(statements)
  } getOrElse new LinkedHashModel
}

