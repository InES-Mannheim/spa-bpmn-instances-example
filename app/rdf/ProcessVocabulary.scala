package rdf

import org.openrdf.model.IRI
import IriImplicits._

trait ProcessVocabulary {
  val Process:IRI = "http://dkm.fbk.eu/index.php/BPMN2_Ontology#process"
  val SequenceFlow:IRI = "http://dkm.fbk.eu/index.php/BPMN2_Ontology#sequenceFlow"
  val ExclusiveGateway:IRI = "http://dkm.fbk.eu/index.php/BPMN2_Ontology#exclusiveGateway"
  val ScriptTask:IRI = "http://dkm.fbk.eu/index.php/BPMN2_Ontology#globalScriptTask"
  val StartEvent:IRI = "http://dkm.fbk.eu/index.php/BPMN2_Ontology#startEvent"
  val EndEvent:IRI = "http://dkm.fbk.eu/index.php/BPMN2_Ontology#endEvent"
}
