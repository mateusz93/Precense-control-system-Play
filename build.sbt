name := "play-presence-control-system"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)
lazy val myProject = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.12.2"

//libraryDependencies += jdbc

libraryDependencies += guice

// Test Database
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.41"
libraryDependencies += "com.h2database" % "h2" % "1.4.194"

// Testing libraries for dealing with CompletionStage...
libraryDependencies += "org.assertj" % "assertj-core" % "3.6.2" % Test
libraryDependencies += "org.awaitility" % "awaitility" % "2.0.0" % Test

libraryDependencies += "org.glassfish" % "javax.json" % "1.0.4"
libraryDependencies += "javax.servlet" % "javax.servlet-api" % "3.1.0"

libraryDependencies += "org.projectlombok" % "lombok" % "1.16.16"

libraryDependencies += "org.apache.commons" % "commons-email" % "1.5"

libraryDependencies += "org.apache.commons" % "commons-collections4" % "4.1"
libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.6"

libraryDependencies += "commons-fileupload" % "commons-fileupload" % "1.3.3"

libraryDependencies += "org.apache.pdfbox" % "pdfbox" % "2.0.8"
libraryDependencies += "org.apache.pdfbox" % "fontbox" % "2.0.8"
