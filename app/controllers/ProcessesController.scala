package controllers

import java.io.ByteArrayOutputStream
import javax.inject.Inject

import dal.ProcessRepository
import org.openrdf.model.Model
import org.openrdf.rio.{RDFFormat, Rio}
import play.api.libs.json._
import play.api.mvc.{Action, Controller}

class ProcessesController @Inject()(repo: ProcessRepository) extends Controller {

//  def create = Action(parse.json) { implicit request =>
//    Ok("200")
//  }

  def list = Action { implicit request =>
    val model: Model = repo.findAll
    if(model.isEmpty) Status(NOT_FOUND)
    else Ok(Json.toJson(model)(ProcessesController.modelWrites)).as("application/ld+json")
  }
}

object ProcessesController {
  implicit val modelWrites = new Writes[Model] {
    override def writes(o: Model): JsValue = {
      val baos: ByteArrayOutputStream = new ByteArrayOutputStream()
      Rio.write(o, baos, RDFFormat.JSONLD)
      Json.parse(baos.toByteArray)
    }
  }
}