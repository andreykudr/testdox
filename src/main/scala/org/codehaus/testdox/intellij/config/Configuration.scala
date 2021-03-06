package org.codehaus.testdox.intellij.config

import org.codehaus.testdox.intellij.TemplateNameResolver

import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import scala.reflect.BeanProperty
import scalaj.collection.Imports._

@serializable
class Configuration {

  private var customPackages = List[String]()
  private var customPackagesAllowed: Boolean = false
  private var testNameTemplate = TemplateNameResolver.DEFAULT_TEMPLATE
  private var showFullyQualifiedClassName: Boolean = false

  @BeanProperty var alphabeticalSorting = false
  @BeanProperty var createTestIfMissing = true
  @BeanProperty var underscoreMode = false
  @BeanProperty var autoScrolling = false
  @BeanProperty var autoApplyChangesToTests = true
  @BeanProperty var deletePackageOccurrences = false
  @BeanProperty var testMethodPrefix = TemplateNameResolver.DEFAULT_PREFIX
  @BeanProperty var testMethodAnnotation = "@Test"
  @BeanProperty var usingAnnotations = false

  @transient
  private val support = new PropertyChangeSupport(this)

  def addPropertyChangeListener(listener: PropertyChangeListener) {
    support.addPropertyChangeListener(listener)
  }

  def removePropertyChangeListener(listener: PropertyChangeListener) {
    support.removePropertyChangeListener(listener)
  }

  @deprecated("will be replaced with 'public var customPackagesAllowed'")
  def getCustomPackagesAllowed = customPackagesAllowed

  @deprecated("will be replaced with 'public var customPackagesAllowed'")
  def setCustomPackagesAllowed(customPackagesAllowed: Boolean) {
    val old = this.customPackagesAllowed
    this.customPackagesAllowed = customPackagesAllowed
    support.firePropertyChange(Configuration.ALLOW_CUSTOM_PACKAGES, old, customPackagesAllowed)
  }

  @deprecated("will be replaced with 'public var customPackages'")
  def getCustomPackages = customPackages

  @deprecated("will be replaced with 'public var customPackages'")
  def getCustomPackagesAsJavaList = customPackages.asJava

  @deprecated("will be replaced with 'public var customPackages'")
  def setCustomPackages(newPackages: java.util.List[String]) { setCustomPackages(newPackages.asScala.toList) }

  @deprecated("will be replaced with 'public var customPackages'")
  def setCustomPackages(newPackages: List[String]) {
    val oldPackages = customPackages
    customPackages = newPackages
    support.firePropertyChange(Configuration.CUSTOM_PACKAGES, oldPackages, customPackages)
  }

  @deprecated("will be replaced with 'public var testNameTemplate'")
  def getTestNameTemplate = testNameTemplate

  @deprecated("will be replaced with 'public var testNameTemplate'")
  def setTestNameTemplate(testNameTemplate: String) {
    val old = this.testNameTemplate
    this.testNameTemplate = testNameTemplate
    support.firePropertyChange(Configuration.TEST_NAME_TEMPLATE, old, testNameTemplate)
  }

  @deprecated("will be replaced with 'public var showFullyQualifiedClassName'")
  def getShowFullyQualifiedClassName = showFullyQualifiedClassName

  @deprecated("will be replaced with 'public var showFullyQualifiedClassName'")
  def setShowFullyQualifiedClassName(showFullyQualifiedClassName: Boolean) {
    val oldValue = this.showFullyQualifiedClassName
    this.showFullyQualifiedClassName = showFullyQualifiedClassName
    support.firePropertyChange(Configuration.SHOW_FULLY_QUALIFIED_CLASS_NAME, oldValue, showFullyQualifiedClassName)
  }

  def testMethodIndicator = if (usingAnnotations) testMethodAnnotation else testMethodPrefix
}

object Configuration {
  val ALLOW_CUSTOM_PACKAGES = "allowCustomPackages"
  val CUSTOM_PACKAGES = "customPackages"
  val TEST_NAME_TEMPLATE = "testNameTemplate"
  val SHOW_FULLY_QUALIFIED_CLASS_NAME = "showFullyQualifiedClassName"
}
