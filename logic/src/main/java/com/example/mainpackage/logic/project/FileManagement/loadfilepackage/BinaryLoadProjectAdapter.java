package com.example.mainpackage.logic.project.FileManagement.loadfilepackage;

import com.example.mainpackage.logic.project.Project;
import com.example.mainpackage.logic.user.User;

public class BinaryLoadProjectAdapter extends LoadProject{

    BinaryLoadProject binaryLoadProject;

    public BinaryLoadProjectAdapter() {
        this.binaryLoadProject = new BinaryLoadProject();
    }
    
    
    @Override
    public Project loadProject(String filePathProject, User user) {
        return binaryLoadProject.loadBinaryProject(filePathProject, user);
    }
    
}
