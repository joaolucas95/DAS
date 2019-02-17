package com.example.mainpackage.logic.project.FileManagement.loadfilepackage;

import com.example.mainpackage.logic.project.FileManagement.File;
import com.example.mainpackage.logic.project.Project;

public class BinaryLoadProject {
    
    public Project loadBinaryProject(String fileName){
        return File.loadProject("", fileName);
    }
}
