package controllers

import java.io.ByteArrayOutputStream

import org.openrdf.model.Model
import org.openrdf.rio.{Rio, RDFFormat}
import play.api.libs.json._

case class ModelWrapper(model: Model) {
}

object ModelWrapper {
  implicit val modelWrites = new Writes[ModelWrapper] {
    override def writes(o: ModelWrapper): JsValue = {
      val baos: ByteArrayOutputStream = new ByteArrayOutputStream()
      Rio.write(o.model, baos, RDFFormat.JSONLD)
      Json.parse(baos.toByteArray)
    }
  }
}
