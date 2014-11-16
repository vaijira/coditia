
organization := "com.coditia"

name := "coditia"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.2"

scalacOptions += "-deprecation"

seq(webSettings :_*)

libraryDependencies ++= {
  val liftVersion = "2.6-RC1"
  Seq(
    "org.scala-lang.modules" %% "scala-xml" % "1.0.2",
    "net.liftweb" %% "lift-webkit" % liftVersion % "compile",
    "net.liftweb" %% "lift-squeryl-record" % liftVersion,
    "com.zaxxer" % "HikariCP-java6" % "2.2.4" % "compile",
    "org.postgresql" % "postgresql" % "9.3-1102-jdbc41",
    "com.h2database" % "h2" % "1.4.182",
    "org.eclipse.jetty" % "jetty-webapp" % "8.1.7.v20120910"  % "container,test",
    "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" %
      "container,compile" artifacts Artifact("javax.servlet", "jar", "jar")
  )
}


