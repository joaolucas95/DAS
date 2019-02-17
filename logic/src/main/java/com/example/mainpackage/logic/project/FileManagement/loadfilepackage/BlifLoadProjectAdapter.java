package com.example.mainpackage.logic.project.FileManagement.loadfilepackage;


import com.example.mainpackage.logic.project.Project;

public class BlifLoadProjectAdapter extends LoadProject{

    BlifLoadProject flBlifLoadProject;

    public BlifLoadProjectAdapter() {
        this.flBlifLoadProject = new BlifLoadProject();
    }
    
    
    @Override
    public Project loadProject(String fileName) {
        return flBlifLoadProject.loadBlifProject(fileName);
    }
    
}