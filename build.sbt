name := """spa-rest-process-instances"""

version := "0.0.1-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

resolvers += Resolver.jcenterRepo

resolvers += "dl-john-ky" at "http://dl.john-ky.io/maven/releases"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test
)

libraryDependencies ++= Seq(
  "com.iheart" %% "play-swagger" % "0.1.5-RELEASE",
  "org.openrdf.sesame" % "sesame-sail-memory" % "4.0.0",
  "org.openrdf.sesame" % "sesame-repository-sail" % "4.0.0",
  "org.openrdf.sesame" % "sesame-rio-jsonld" % "4.0.0",
  "org.openrdf.sesame" % "sesame-rio-rdfxml" % "4.0.0",
  "com.github.jsonld-java" % "jsonld-java" % "0.7.0",
  "io.john-ky" %% "hashids-scala" % "1.1.0-b33fd4e"
)

libraryDependencies += "org.webjars" % "swagger-ui" % "2.1.2"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
