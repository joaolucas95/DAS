package com.example.mainpackage;

import com.example.mainpackage.logic.dblogic.FileHistoryViewModel;
import com.example.mainpackage.logic.dblogic.FilePath;
import com.example.mainpackage.logic.project.Project;
import com.example.mainpackage.logic.project.component.Component;
import com.example.mainpackage.logic.project.component.ComponentType;
import com.example.mainpackage.logic.project.component.ComponentUtils;
import com.example.mainpackage.logic.project.filemanagement.FileType;
import com.example.mainpackage.logic.project.filemanagement.FileUtils;
import com.example.mainpackage.logic.project.filemanagement.ProjectFileManagement;
import com.example.mainpackage.logic.statemachinepackage.ComponentEditorStateMachine;
import com.example.mainpackage.logic.user.User;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentActivity;

import java.util.List;

public class LogicFacadeImp implements ILogicFacade {

    private ComponentEditorStateMachine mEditorStateMachine;

    @Override
    public void setCurrentUsername(String username) {
        User.getInstance().setUsername(username);
    }

    @Override
    public String getCurrentUsername() {
        return User.getInstance().getUsername();
    }

    @Override
    public List<ComponentType> getComponentsTypes() {
        return mEditorStateMachine.getComponentTypes();
    }

    @Override
    public String getComponentTypeName(ComponentType type) {
        return ComponentUtils.getComponentName(type);
    }

    @Override
    public List<FileType> getFileTypes() {
        return FileUtils.getFileTypes();
    }

    @Override
    public String getFileTypeName(FileType type) {
        return FileUtils.getFileTypeName(type);
    }


    @Override
    public Project getProject(String filePath) throws Exception {
        ProjectFileManagement projectFileManagement = new ProjectFileManagement();
        return projectFileManagement.loadProject(filePath, User.getInstance());
    }

    @Override
    public void removeProject(String filePath) {
        ProjectFileManagement projectFileManagement = new ProjectFileManagement();
        projectFileManagement.removeProject(filePath);
    }

    @Override
    public boolean saveProject(Project project, boolean isNewProject, String filePath, FileType fileType) {
        boolean result;

        if (isNewProject) {
            Component module = mEditorStateMachine.finishComponentEditor();
            project.setComponentModule(module);
        }
        ProjectFileManagement projectFileManagement = new ProjectFileManagement();
        result = projectFileManagement.saveProject(project, filePath, fileType);
        return result;
    }

    /* Edition actions */

    @Override
    public void newEdition(boolean isSimpleProject) {
        ComponentType type = isSimpleProject ? ComponentType.MODULE : ComponentType.PROJECT;
        mEditorStateMachine = new ComponentEditorStateMachine(type);
    }

    @Override
    public List<Component> getActualComponents() {
        return mEditorStateMachine.getActualDataToDraw();
    }

    @Override
    public void cancelConnection() {
        mEditorStateMachine.cancelDefiningPrevious();
    }

    @Override
    public void selectComponent(String name) {
        mEditorStateMachine.selectComponent(name);
    }

    @Override
    public void addComponent(ComponentType type, int[] pos) {
        mEditorStateMachine.addSimpleComponent(type, pos);
    }

    @Override
    public void addModule(String filePathProject, com.example.mainpackage.logic.user.User user, int[] position) {
        mEditorStateMachine.addModule(filePathProject, user, position);
    }

    @Override
    public void undoOperation() {
        mEditorStateMachine.undoOperation();
    }

    @Override
    public void redoOperation() {
        mEditorStateMachine.redoOperation();
    }

    @Override
    public com.example.mainpackage.logic.dblogic.User findUserByUsername(String username, FragmentActivity fragmentActivity) {
        FileHistoryViewModel mFileHistoryViewModel = ViewModelProviders.of(fragmentActivity).get(FileHistoryViewModel.class);
        return mFileHistoryViewModel.findUserByUsername(username);
    }

    @Override
    public LiveData<List<FilePath>> getAllFilesPathOfUser(int user_id, FragmentActivity fragmentActivity) {
        FileHistoryViewModel mFileHistoryViewModel = ViewModelProviders.of(fragmentActivity).get(FileHistoryViewModel.class);
        return mFileHistoryViewModel.getAllFilesPathOfUser(user_id);
    }

    @Override
    public void deleteFilePath(FilePath filePath, FragmentActivity fragmentActivity) {
        FileHistoryViewModel mFileHistoryViewModel = ViewModelProviders.of(fragmentActivity).get(FileHistoryViewModel.class);
        mFileHistoryViewModel.deleteFilePath(filePath);
    }

    @Override
    public FilePath findFilePathEntityByProjectName(String projectName, FragmentActivity fragmentActivity) {
        FileHistoryViewModel mFileHistoryViewModel = ViewModelProviders.of(fragmentActivity).get(FileHistoryViewModel.class);
        return mFileHistoryViewModel.findFilePathEntityByProjectName(projectName);
    }

    @Override
    public void insertFilePath(FilePath filePath, FragmentActivity fragmentActivity) {
        FileHistoryViewModel mFileHistoryViewModel = ViewModelProviders.of(fragmentActivity).get(FileHistoryViewModel.class);
        mFileHistoryViewModel.insertFilePath(filePath);
    }
}