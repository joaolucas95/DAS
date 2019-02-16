/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.mainpackage.logic.project.FileManagement.SaveFilePackage;

import com.example.mainpackage.logic.utils.Config;
import com.example.mainpackage.logic.project.Project;

/**
 *
 * @author BrunoCoelho
 */
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
