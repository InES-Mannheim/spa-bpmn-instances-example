package controllers

import javax.inject.Inject

import dal.ProcessRepository
import org.openrdf.model.Model
import org.openrdf.rio.helpers.StatementCollector
import play.api.libs.json._
import play.api.mvc.{Action, Controller}

import play.api.mvc.BodyParsers.parse

import scala.util.{Failure, Success}

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

  def create = Action(parse.tolerantJson) { implicit request =>
    request.body.validate[ModelWrapper] match {
      case JsSuccess(wrapper, _) => repo.create(wrapper.model) match {
        case Success(iri) => Ok(iri.toString)
        case Failure(e) => InternalServerError(e.toString)
      }
      case JsError(e) => BadRequest(e.toString)
    }
  }
}
