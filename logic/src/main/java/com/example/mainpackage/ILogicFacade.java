package com.example.mainpackage;

import com.example.mainpackage.logic.project.FileManagement.FileType;
import com.example.mainpackage.logic.project.Project;
import com.example.mainpackage.logic.project.component.Component;
import com.example.mainpackage.logic.project.component.ComponentType;

import java.util.List;

public interface ILogicFacade {

    void setCurrentUsername(String username);

    String getCurrentUsername();

    List<ComponentType> getComponentsTypes();

    String getComponentTypeName(ComponentType type);

    List<FileType> getFileTypes();

    String getFileTypeName(FileType type);

    Project getProject(String filePath) throws Exception;

    boolean saveProject(Project project, String filePath, FileType fileType);

    /* Edit actions */

    void newEdition(boolean isSimpleProject);

    List<Component> getActualComponents();

    void cancelConnection();

    void selectComponent(String name);

    void addComponent(ComponentType type, int[] pos);

    void undoOperation();

    void redoOperation();
}