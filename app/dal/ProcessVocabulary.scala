package dal

import org.openrdf.model.IRI
import org.openrdf.model.impl.SimpleValueFactory

trait ProcessVocabulary {
  import scala.language.implicitConversions

  val Process:IRI = "http://dkm.fbk.eu/index.php/BPMN2_Ontology#process"
  val SequenceFlow:IRI = "http://dkm.fbk.eu/index.php/BPMN2_Ontology#sequenceFlow"
  val ExclusiveGateway:IRI = "http://dkm.fbk.eu/index.php/BPMN2_Ontology#exclusiveGateway"
  val ScriptTask:IRI = "http://dkm.fbk.eu/index.php/BPMN2_Ontology#globalScriptTask"
  val StartEvent:IRI = "http://dkm.fbk.eu/index.php/BPMN2_Ontology#startEvent"
  val EndEvent:IRI = "http://dkm.fbk.eu/index.php/BPMN2_Ontology#endEvent"

  implicit def toIri(s:String):IRI = SimpleValueFactory.getInstance().createIRI(s)
}
