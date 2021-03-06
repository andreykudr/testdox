package org.codehaus.testdox.intellij.actions

import com.intellij.openapi.actionSystem.AnActionEvent

import org.codehaus.testdox.intellij.Icons
import org.codehaus.testdox.intellij.Icons._

class SortTestDoxAction(private var selected: Boolean, useFromTestDoxToolWindow: Boolean)
    extends BaseToggleAction("Sort Alphabetically", "Toggle sorting", getIcon(Icons.SORT_ICON), useFromTestDoxToolWindow) {

  def this() = this(false, false)

  def isSelected(event: AnActionEvent) = selected

  def setSelected(event: AnActionEvent, selected: Boolean) {
    this.selected = selected
    if (event != null) actionEvents.getTestDoxController(event).updateSort(selected)
  }
}

object SortTestDoxAction {
  val ID = "TestDox.SortTestDox"
}
