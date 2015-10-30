package rdf

import org.openrdf.model.IRI
import org.openrdf.model.impl.SimpleValueFactory

object IriImplicits {
  import scala.language.implicitConversions
  implicit def toIri(s:String):IRI = SimpleValueFactory.getInstance().createIRI(s)
}
