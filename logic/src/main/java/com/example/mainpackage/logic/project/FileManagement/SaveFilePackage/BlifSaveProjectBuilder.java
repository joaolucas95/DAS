package com.example.mainpackage.logic.project.FileManagement.SaveFilePackage;

import com.example.mainpackage.logic.project.FileManagement.File;
import com.example.mainpackage.logic.project.Project;

public class BlifSaveProjectBuilder extends SaveProjectBuilder{

    @Override
    public boolean saveProject(String fileName, Project project) {
        return File.saveProjectAsBlifFile("", project);
    }
    
}
