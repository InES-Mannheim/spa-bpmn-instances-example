package controllers

import javax.inject.Inject

import dal.ProcessRepository
import org.openrdf.model.Model
import play.api.libs.json._
import play.api.mvc.{Action, Controller}

class ProcessesController @Inject()(repo: ProcessRepository) extends Controller {

  def list = Action { implicit request =>
    val model: Model = repo.findAll
    if(model.isEmpty) Status(NOT_FOUND)
    else Ok(Json.toJson(ModelWrapper(model))).as("application/ld+json")
  }

  def findById(id:String) = Action { implicit request =>
    val model: Model = repo.findById(id)
    if(model.isEmpty) Status(NOT_FOUND)
    else Ok(Json.toJson(ModelWrapper(model))).as("application/ld+json")
  }
}
