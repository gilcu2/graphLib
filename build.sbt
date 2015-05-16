name := "graphLib"

version := "0.1.0"

scalaVersion := "2.11.6"

libraryDependencies += "org.graphstream" % "gs-core" % "1.3"

libraryDependencies += "org.graphstream" % "gs-algo" % "1.3"

libraryDependencies += "org.codehaus.jackson" % "jackson-core-asl" % "1.1.0"  % "test"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"

libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"

testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v")


//sources in Compile <<= (sources in Compile).map(_ filter(x=>x.name != "pruebaConexion.java"))