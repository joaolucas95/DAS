package com.example.mainpackage;

import com.example.mainpackage.logic.project.FileManagement.FileType;
import com.example.mainpackage.logic.project.FileManagement.FileUtils;
import com.example.mainpackage.logic.project.FileManagement.ProjectFileManagement;
import com.example.mainpackage.logic.project.Project;
import com.example.mainpackage.logic.project.component.ComponentType;
import com.example.mainpackage.logic.project.component.ComponentUtils;
import com.example.mainpackage.logic.user.User;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogicFacadeImp implements ILogicFacade {

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
        return ComponentUtils.getComponentsTypes();
    }

    @Override
    public String getComponentsTypeName(ComponentType type) {
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
        Project project = projectFileManagement.loadProject(filePath, User.getInstance());
        return project;
    }

    @Override
    public boolean saveProject(Project project, String filePath, FileType fileType) throws Exception {
        boolean result;
        ProjectFileManagement projectFileManagement = new ProjectFileManagement();
        result = projectFileManagement.saveProject(project, filePath, fileType);
        return result;
    }

    @Override
    public String getComponentTypeName(ComponentType type) {
        return null;
    }
}