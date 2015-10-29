package controllers

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

import info.aduna.iteration.Iterations
import org.openrdf.model.Model
import org.openrdf.model.impl.LinkedHashModel
import org.openrdf.rio.helpers.StatementCollector
import org.openrdf.rio.{Rio, RDFFormat}
import play.api.libs.json._

import scala.util.{Failure, Success, Try}

case class ModelWrapper(model: Model) {
}

object ModelWrapper {

  implicit val modelWrites = new Writes[ModelWrapper] {
    override def writes(o: ModelWrapper): JsValue = {
      val baos = new ByteArrayOutputStream()
      Rio.write(o.model, baos, RDFFormat.JSONLD)
      Json.parse(baos.toByteArray)
    }
  }

  implicit val modelReads = new Reads[ModelWrapper] {
    override def reads(json: JsValue): JsResult[ModelWrapper] = {
      val collector = new StatementCollector
      val in = new ByteArrayInputStream(json.toString().getBytes)
      Try(Rio.createParser(RDFFormat.JSONLD).setRDFHandler(collector).parse(in, "http://localhost:9000")) match {
        case Success(_) => JsSuccess(ModelWrapper(new LinkedHashModel(collector.getStatements)))
        case Failure(ex) => JsError.apply(JsPath \ "parseError", ex.toString)
      }
    }
  }

  implicit val modelFormat: Format[ModelWrapper]= Format(modelReads, modelWrites)
}
