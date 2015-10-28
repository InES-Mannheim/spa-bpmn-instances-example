package controllers

import javax.inject.Inject

import dal.ProcessInstanceRepository
import org.openrdf.model.Model
import play.api.libs.json.Json
import play.api.mvc.{Controller, Action}

class ProcessInstancesController @Inject()(repo:ProcessInstanceRepository) extends Controller {

  def listInstancesForProcess(processId:String) = Action { implicit request =>
    val model: Model = repo.findAllInstancesForProcess(processId)
    if(model.isEmpty) Status(NOT_FOUND)
    else Ok(Json.toJson(ModelWrapper(model))).as("application/ld+json")
  }

  def findInstanceForProcessById(processId:String, instanceId:String) = Action { implicit request =>
    val model: Model = repo.findInstanceForProcess(processId, instanceId)
    if(model.isEmpty) Status(NOT_FOUND)
    else Ok(Json.toJson(ModelWrapper(model))).as("application/ld+json")
  }
}
