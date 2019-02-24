package com.example.mainpackage.logic.project.FileManagement.SaveFilePackage;

import com.example.mainpackage.logic.project.FileManagement.FileType;
import com.example.mainpackage.logic.project.Project;

public abstract class SaveProjectBuilder {
    
    public abstract boolean saveProject(String fileName, Project project);

    boolean saveDb(String fileName){
        //TODO:
        //When applying the project with Android Studio
        
        return true;
    }
    public static SaveProjectBuilder getBuilder(FileType type){
        if(type == FileType.BINARY)
            return new BinarySaveProjectBuilder();
        else if(type == FileType.BLIF)
            return new BlifSaveProjectBuilder();
        
        throw new IllegalStateException("Invalid type:" + type);
    }
}
