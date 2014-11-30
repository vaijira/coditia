
organization := "com.coditia"

name := "coditia"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.2"

scalacOptions += "-deprecation"

libraryDependencies ++= {
  val liftVersion = "2.6-RC2"
  Seq(
    "antlr"                  %  "antlr"                       % "2.7.7",
    "commons-collections"    % "commons-collections"          % "3.2.1",
    "commons-logging"        %  "commons-logging"             % "1.1.1",
    "commons-pool"           %  "commons-pool"                % "1.5.7",
    "com.google.collections" %  "google-collections"          % "1.0",
    "com.h2database"         %  "h2"                          % "1.4.182",
    "com.zaxxer"             %  "HikariCP-java6"              % "2.2.4"            % "compile",
    "jgroups"                %  "jgroups-all"                 % "2.2.9.1",
    "javax.transaction"      %  "jta"                         % "1.1",
    "net.liftweb"            %% "lift-webkit"                 % liftVersion        % "compile",
    "net.liftweb"            %% "lift-squeryl-record"         % liftVersion,
    "org.apache.lucene"      % "lucene-core"                  % "2.4.1",
    "org.apache.lucene"      % "lucene-regex"                 % "2.4.1",
    "log4j"                  %  "log4j"                       % "1.2.17",
    "ch.qos.logback"         %  "logback-core"                % "1.0.+",
    "ch.qos.logback"         %  "logback-classic"             % "1.0.+",
    "org.postgresql"         %  "postgresql"                  % "9.3-1102-jdbc41",
    "org.scalatest"          %% "scalatest"                   % "2.2.1"            % "test",
    "org.scalamock"          %% "scalamock-scalatest-support" % "3.2"              % "test",
    "org.scala-lang.modules" %% "scala-xml"                   % "1.0.2",
    "xerces"                 %  "xercesImpl"                  % "2.9.1",
    "xml-resolver"           %  "xml-resolver"                % "1.2"
  )
}

jetty()

