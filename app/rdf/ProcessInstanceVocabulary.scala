package rdf

import IriImplicits._
import org.openrdf.model.IRI

trait ProcessInstanceVocabulary {
  val ProcessInstance:IRI = "http://www.uni-mannheim.de/spa/bpmn2.0/instances.owl#processInstance"
  val ScriptTaskInstance:IRI = "http://www.uni-mannheim.de/spa/bpmn2.0/instances.owl#globalScriptTaskInstance"
  val StartEventInstance:IRI = "http://www.uni-mannheim.de/spa/bpmn2.0/instances.owl#startEventInstance"
  val EndEventInstance:IRI = "http://www.uni-mannheim.de/spa/bpmn2.0/instances.owl#endEventInstance"

  val isInstanceOfProcess:IRI = "http://www.uni-mannheim.de/spa/bpmn2.0/instances.owl#isInstanceOfProcess"
  val isInstanceOfFlowElement:IRI = "http://www.uni-mannheim.de/spa/bpmn2.0/instances.owl#isInstanceOfFlowElement"
  val followedBy:IRI = "http://www.uni-mannheim.de/spa/bpmn2.0/instances.owl#followedBy"
}
