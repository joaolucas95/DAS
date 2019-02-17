package com.example.mainpackage.logic.project.FileManagement.SaveFilePackage;

import com.example.mainpackage.logic.project.FileManagement.File;
import com.example.mainpackage.logic.project.Project;

public class BinarySaveProjectBuilder extends SaveProjectBuilder{

    @Override
    public boolean saveProject(String fileName, Project project) {
        
        fileName +=".bin";
        
        if(!saveDb(fileName))
            return false;
        
        return File.saveProjectAsBinFile("", fileName, project);

    }

    
}
