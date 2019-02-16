/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.mainpackage.logic.project.FileManagement.SaveFilePackage;


import com.example.mainpackage.logic.project.FileManagement.File;
import com.example.mainpackage.logic.project.Project;

/**
 *
 * @author BrunoCoelho
 */
public class BinarySaveProjectBuilder extends SaveProjectBuilder{

    @Override
    public boolean saveProject(String fileName, Project project) {
        
        fileName +=".bin";
        
        if(!saveDb(fileName))
            return false;
        
        return File.saveProjectAsBinFile("", fileName, project);

    }

    
}
