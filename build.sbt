
organization := "com.coditia"

name := "coditia"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.2"

scalacOptions ++= Seq(
    "-deprecation",
    "-feature"
)

fork in run := true

fork in Test := true

javaOptions in run ++= Seq(
    "-Djava.library.path=/opt/oracle/dbxml-2.5.16/lib",
    "-Dexist.initdb=true",
    "-Dexist.home=/tmp/eXist"
)

javaOptions in Test ++= Seq(
    "-Djava.library.path=/opt/oracle/dbxml-2.5.16/lib",
    "-Dexist.initdb=true",
    "-Dexist.home=/tmp/eXist"
)

resolvers += "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/"

resolvers += "sonatype-snapshots" at "https://oss.sonatype.org/content/groups/public/"

libraryDependencies ++= {
  val liftVersion = "2.6.2"
  Seq(
    "antlr"                  %  "antlr"                       % "2.7.7",
    "commons-collections"    %  "commons-collections"         % "3.2.1",
    "commons-logging"        %  "commons-logging"             % "1.1.1",
    "commons-pool"           %  "commons-pool"                % "1.5.7",
    "com.google.collections" %  "google-collections"          % "1.0",
    "com.h2database"         %  "h2"                          % "1.4.182",
    "com.zaxxer"             %  "HikariCP-java6"              % "2.2.4"            % "compile",
    "jgroups"                %  "jgroups-all"                 % "2.2.9.1",
    "javax.transaction"      %  "jta"                         % "1.1",
    "net.liftmodules"        %% "widgets_2.6"                 % "1.4-SNAPSHOT",
    "net.liftweb"            %% "lift-webkit"                 % liftVersion        % "compile",
    "net.liftweb"            %% "lift-squeryl-record"         % liftVersion,
    "org.apache.lucene"      %  "lucene-core"                 % "2.4.1",
    "org.apache.lucene"      %  "lucene-regex"                % "2.4.1",
    "log4j"                  %  "log4j"                       % "1.2.17",
    "ch.qos.logback"         %  "logback-core"                % "1.0.+",
    "ch.qos.logback"         %  "logback-classic"             % "1.0.+",
    "org.postgresql"         %  "postgresql"                  % "9.3-1102-jdbc41",
    "org.scalatest"          %% "scalatest"                   % "2.2.1"            % "test",
    "org.scalamock"          %% "scalamock-scalatest-support" % "3.2"              % "test",
    "org.mockito"            %  "mockito-core"                % "1.10.19"          % "test",
    "org.scala-lang.modules" %% "scala-xml"                   % "1.0.2",
    "xerces"                 %  "xercesImpl"                  % "2.9.1",
    "xml-resolver"           %  "xml-resolver"                % "1.2"
  )
}

jetty()

