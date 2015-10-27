package controllers

import com.iheart.playSwagger.SwaggerSpecGenerator
import play.api.cache.Cached
import play.api.mvc._
import javax.inject._
import play.api.routing._


class ApiSpecs @Inject() (router: Router, cached: Cached) extends Controller {
  implicit val cl = getClass.getClassLoader

  // The root package of your domain classes, PlaySwagger will automatically generate definitions when it encounters class references in this package.
  // In our case it would be "com.iheart"
  val domainPackage = "controllers"
  private lazy val generator = SwaggerSpecGenerator(domainPackage)

  def specs = cached("swaggerDef") {  //it would be beneficial to cache this endpoint as we do here, but it's not required if you don't expect much traffic.
    Action { _ ⇒
      Ok(generator.generate(router.documentation))
    }
  }

}
