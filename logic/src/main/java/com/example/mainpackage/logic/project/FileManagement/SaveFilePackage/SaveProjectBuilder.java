package com.example.mainpackage.logic.project.FileManagement.SaveFilePackage;

import com.example.mainpackage.logic.project.Project;

import mainpackage.Config;

public abstract class SaveProjectBuilder {
    
    public abstract boolean saveProject(String fileName, Project project);

    boolean saveDb(String fileName){
        //TODO:
        //When applying the project with Android Studio
        
        return true;
    }
    public static SaveProjectBuilder getBuilder(int type){
        if(type == Config.FILE_TYPE_BINARY)
            return new BinarySaveProjectBuilder();
        else if(type == Config.FILE_TYPE_BLIF)
            return new BlifSaveProjectBuilder();
        
        return null;
    }
}
