package com.example.mainpackage.logic.project.FileManagement.loadfilepackage;

import com.example.mainpackage.logic.project.Project;

public class BinaryLoadProjectAdapter extends LoadProject{

    BinaryLoadProject binaryLoadProject;

    public BinaryLoadProjectAdapter() {
        this.binaryLoadProject = new BinaryLoadProject();
    }
    
    
    @Override
    public Project loadProject(String fileName) {
        return binaryLoadProject.loadBinaryProject(fileName);
    }
    
}
