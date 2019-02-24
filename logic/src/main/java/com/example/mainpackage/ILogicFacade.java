package com.example.mainpackage;

import com.example.mainpackage.logic.project.component.ComponentType;

import java.util.List;

public interface ILogicFacade {

    void setCurrentUsername(String username);

    String getCurrentUsername();

    List<ComponentType> getComponentsTypes();

    String getComponentTypeName(ComponentType type);

    List<FileType> getFileTypes();

    String getFileTypeName(FileType type);
    String getComponentsTypeName(ComponentType type);

    Project getProject(String filePath);
}