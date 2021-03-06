package org.codehaus.testdox.intellij

import org.codehaus.testdox.intellij.SentenceTranslator._
import org.codehaus.testdox.intellij.config.Configuration

class SentenceTranslator(configuration: Configuration) {

  def buildSentence(methodName: String): String = methodName match {
    case null => ""
    case "" => ""
    case _ => methodName
        .replaceFirst(configuration.testMethodPrefix, "")
        .replaceAll("([a-z])([A-Z])", "$1 $2")
        .replaceAll("([a-z])([\\d]{2,})", "$1 $2")
        .replaceAll("([\\d]{2,})([A-Z])", "$1 $2")
        .replaceAll("[_\\s]+", " ")
        .split("[_\\s]")
        .map { token => if (token.matches("[A-Z][a-z]*")) token.toLowerCase else token }
        .mkString(" ")
  }

  def buildMethodName(sentence: String): String = sentence match {
    case null => DEFAULT_TEST_METHOD_NAME
    case "" => DEFAULT_TEST_METHOD_NAME
    case _ => {
      val methodName = sentence
          .split("\\s+")
          .map { token => if (configuration.underscoreMode && token.matches(ACRONYM_REGEXP)) "_" + token + "_" else token }
          .map { token => token.charAt(0).toUpper + token.substring(1) }
          .mkString("")

      if (configuration.usingAnnotations) {
        methodName.substring(0, 1).toLowerCase + methodName.substring(1)
      } else {
        configuration.testMethodPrefix + methodName
      }
    }
  }
}

object SentenceTranslator {
  val DEFAULT_TEST_METHOD_NAME = "testNoInformationKnownAboutThisTest"
  val ACRONYM_REGEXP = "[A-Z]{2,}"
}
