package org.codehaus.testdox.intellij;

import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.refactoring.listeners.RefactoringElementListenerProvider;
import org.codehaus.testdox.intellij.actions.PresentationUpdater;
import org.codehaus.testdox.intellij.config.Configuration;
import org.codehaus.testdox.intellij.ui.TestDoxTableModel;

public interface TestDoxController extends FileEditorManagerListener,
                                           RefactoringElementListenerProvider,
                                           PresentationUpdater {
    EditorApi getEditorApi();

    TestDoxFileFactory getTestDoxFileFactory();

    TestDoxTableModel getModel();

    Configuration getConfiguration();

    void setConfiguration(Configuration configuration);

    void selectedFileChanged(VirtualFile file);

    boolean hasActiveEditors();

    boolean canCurrentFileBeUnitTested();

    TestDoxFile getCurrentTestDoxFile();

    void addTest();

    void delete(PsiElement element);

    void startRename(PsiElement element);

    void startRename(TestMethod testMethod);

    void toggleQuickDox();

    void closeQuickDox();

    void toggleTestClassAndTestedClass();

    void jumpToTestElement(TestElement selectedTestElement, boolean autoScrolling);

    void updateSort(boolean alphabetical);

    void refreshToolWindow();

    void toggleToolWindow();

    void updateAutoScroll(boolean autoScrolling);

    void updatePresentation(Presentation presentation, PsiElement targetPsiElement);
}
