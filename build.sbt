
organization := "com.coditia"

name := "coditia"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.2"

scalacOptions += "-deprecation"

jetty()

libraryDependencies ++= {
  val liftVersion = "2.6-RC1"
  Seq(
    "org.scala-lang.modules" %% "scala-xml"                   % "1.0.2",
    "net.liftweb"            %% "lift-webkit"                 % liftVersion        % "compile",
    "net.liftweb"            %% "lift-squeryl-record"         % liftVersion,
    "com.zaxxer"             %  "HikariCP-java6"              % "2.2.4"            % "compile",
    "org.postgresql"         %  "postgresql"                  % "9.3-1102-jdbc41",
    "com.h2database"         %  "h2"                          % "1.4.182",
    "ch.qos.logback"         %  "logback-core"                % "1.0.+",
    "ch.qos.logback"         %  "logback-classic"             % "1.0.+",
    "org.scalatest"          %% "scalatest"                   % "2.2.1"            % "test",
    "org.scalamock"          %% "scalamock-scalatest-support" % "3.2"              % "test"  )
}


