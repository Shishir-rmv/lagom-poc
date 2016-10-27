organization in ThisBuild := "sample.helloworld"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.11.8"

//#service-locator-port
//lagomServiceLocatorPort in ThisBuild := 10000
//#service-locator-port
lagomUnmanagedServices in ThisBuild := Map("stateTerritory" -> "http://services.groupkt.com:80")
//libraryDependencies in ThisBuild += "com.lightbend.lagom" % "lagom-service-locator-zookeeper_2.11" % "1.0.0-SNAPSHOT"
libraryDependencies in ThisBuild += "com.lightbend.lagom" % "lagom-service-locator-consul_2.11" % "1.0.0-SNAPSHOT"

lazy val helloworldApi = project("helloworld-api")
  .settings(
    version := "1.0-SNAPSHOT",
    libraryDependencies += lagomJavadslApi
  )

lazy val helloworldImpl = project("helloworld-impl")
  .enablePlugins(LagomJava)
  .settings(
    version := "1.0-SNAPSHOT",
    libraryDependencies ++= Seq(
      lagomJavadslPersistence,
      lagomJavadslTestKit
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(helloworldApi)

lazy val hellostreamApi = project("hellostream-api")
  .settings(version := "1.0-SNAPSHOT")
  .settings(
    libraryDependencies += lagomJavadslApi
  )

lazy val hellostreamImpl = project("hellostream-impl")
  .settings(version := "1.0-SNAPSHOT")
  .enablePlugins(LagomJava)
  .dependsOn(hellostreamApi, helloworldApi)
  .settings(
    libraryDependencies += lagomJavadslTestKit
  )

lazy val stateTerritoryServiceApi = project("state-territory-service-api")
  .settings(
    version := "1.0-SNAPSHOT",
    libraryDependencies += lagomJavadslApi
  )
  
lazy val ordinaryServiceApi = project("ordinary-service-api")
  .settings(
    version := "1.0-SNAPSHOT",
    libraryDependencies += lagomJavadslApi
  )

lazy val ordinaryServiceImpl = project("ordinary-service-impl")
  .enablePlugins(LagomJava)
  .settings(
    version := "1.0-SNAPSHOT",
    libraryDependencies ++= Seq(
      lagomJavadslPersistence,
      lagomJavadslTestKit
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(ordinaryServiceApi, helloworldApi, stateTerritoryServiceApi)


def project(id: String) = Project(id, base = file(id))
  .settings(eclipseSettings: _*)
  .settings(javacOptions in compile ++= Seq("-encoding", "UTF-8", "-source", "1.8", "-target", "1.8", "-Xlint:unchecked", "-Xlint:deprecation"))
  .settings(jacksonParameterNamesJavacSettings: _*) // applying it to every project even if not strictly needed.


// See https://github.com/FasterXML/jackson-module-parameter-names
lazy val jacksonParameterNamesJavacSettings = Seq(
  javacOptions in compile += "-parameters"
)

// Configuration of sbteclipse
// Needed for importing the project into Eclipse
lazy val eclipseSettings = Seq(
  EclipseKeys.projectFlavor := EclipseProjectFlavor.Java,
  EclipseKeys.withBundledScalaContainers := false,
  EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource,
  EclipseKeys.eclipseOutput := Some(".target"),
  EclipseKeys.withSource := true,
  EclipseKeys.withJavadoc := true,
  // avoid some scala specific source directories
  unmanagedSourceDirectories in Compile := Seq((javaSource in Compile).value),
  unmanagedSourceDirectories in Test := Seq((javaSource in Test).value)
)

