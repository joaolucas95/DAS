package com.example.mainpackage.logic.project.filemanagement.loadfilepkg;


import com.example.mainpackage.logic.dblogic.FilePath;
import com.example.mainpackage.logic.project.Project;
import com.example.mainpackage.logic.user.User;

public class BlifLoadProjectAdapter extends LoadProject{

    BlifLoadProject flBlifLoadProject;

    public BlifLoadProjectAdapter() {
        this.flBlifLoadProject = new BlifLoadProject();
    }
    
    @Override
    public Project loadProject(String filePathProject, User user) {
        return flBlifLoadProject.loadBlifProject(filePathProject, user);
    }
    
}