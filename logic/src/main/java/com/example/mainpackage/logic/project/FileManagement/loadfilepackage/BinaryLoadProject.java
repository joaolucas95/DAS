/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.mainpackage.logic.project.FileManagement.loadfilepackage;


import com.example.mainpackage.logic.project.FileManagement.File;
import com.example.mainpackage.logic.project.Project;

/**
 *
 * @author BrunoCoelho
 */
public class BinaryLoadProject {
    
    public Project loadBinaryProject(String fileName){
        return File.loadProject("", fileName);
    }
}
