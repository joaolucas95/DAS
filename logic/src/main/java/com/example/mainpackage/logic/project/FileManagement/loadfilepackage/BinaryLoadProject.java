package com.example.mainpackage.logic.project.FileManagement.loadfilepackage;

import com.example.mainpackage.logic.project.FileManagement.File;
import com.example.mainpackage.logic.project.Project;
import com.example.mainpackage.logic.user.User;

public class BinaryLoadProject {
    
    public Project loadBinaryProject(String filePathProject, User user) {
        return File.loadProjectFromBinFile(filePathProject, user);
    }
}
