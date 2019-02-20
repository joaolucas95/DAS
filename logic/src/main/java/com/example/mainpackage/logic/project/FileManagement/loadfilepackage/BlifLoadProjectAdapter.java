package com.example.mainpackage.logic.project.FileManagement.loadfilepackage;


import com.example.mainpackage.logic.project.Project;
import com.example.mainpackage.logic.user.User;

public class BlifLoadProjectAdapter extends LoadProject{

    BlifLoadProject flBlifLoadProject;

    public BlifLoadProjectAdapter() {
        this.flBlifLoadProject = new BlifLoadProject();
    }
    
    
    @Override
    public Project loadProject(String fileName, User user) {
        return flBlifLoadProject.loadBlifProject(fileName, user);
    }
    
}