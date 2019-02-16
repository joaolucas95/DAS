/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.mainpackage.logic.project.FileManagement.loadfilepackage;

import com.example.mainpackage.logic.project.Project;

public class BlifLoadProjectAdapter extends LoadProject {

    BlifLoadProject flBlifLoadProject;

    public BlifLoadProjectAdapter() {
        this.flBlifLoadProject = new BlifLoadProject();
    }
    
    
    @Override
    public Project loadProject(String fileName) {
        return flBlifLoadProject.loadBlifProject(fileName);
    }
    
}