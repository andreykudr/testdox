package org.codehaus.testdox.intellij.inspections

import com.intellij.codeInspection.ProblemHighlightType.GENERIC_ERROR_OR_WARNING
import com.intellij.codeInspection.{LocalQuickFix, InspectionManager, ProblemDescriptor}
import com.intellij.psi.PsiEmptyStatement
import com.intellij.psi.PsiMethod

import org.jetbrains.annotations.NotNull
import org.codehaus.testdox.intellij.TestDoxProjectComponent
import org.codehaus.testdox.intellij.inspections.Inspection.NO_PROBLEMS

class EmptyTestMethodInspection extends Inspection {

  @NotNull
  val getDisplayName = "Empty test"

  override def checkMethod(@NotNull psiMethod: PsiMethod, @NotNull manager: InspectionManager, isOnTheFly: Boolean): Array[ProblemDescriptor] = {
    val testDoxController = TestDoxProjectComponent.getInstance(manager.getProject).getController
    val factory = testDoxController.getTestDoxFileFactory
    val file = factory.getTestDoxFile(psiMethod.getContainingFile.getVirtualFile)

    val editorApi = testDoxController.getEditorApi
    if (file.isTestedClass || !file.canNavigateToTestedClass || !editorApi.isTestMethod(psiMethod)) return NO_PROBLEMS

    val codeBlock = psiMethod.getBody
    if (codeBlock == null) return NO_PROBLEMS

    for (statement <- codeBlock.getStatements) {
      if (!classOf[PsiEmptyStatement].isInstance(statement)) return NO_PROBLEMS
    }
    createProblemDescriptor(manager, psiMethod)
  }

  private def createProblemDescriptor(manager: InspectionManager, psiMethod: PsiMethod): Array[ProblemDescriptor] = {
    Array(manager.createProblemDescriptor(psiMethod.getNameIdentifier, getDisplayName, null.asInstanceOf[LocalQuickFix], GENERIC_ERROR_OR_WARNING, false))
  }
}
