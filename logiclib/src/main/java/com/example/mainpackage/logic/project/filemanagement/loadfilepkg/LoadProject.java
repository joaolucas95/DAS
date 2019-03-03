package com.example.mainpackage.logic.project.filemanagement.loadfilepkg;

import com.example.mainpackage.logic.project.Project;
import com.example.mainpackage.logic.user.User;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class LoadProject {

    public Project loadProject(String filePathProject, User user) {
        Project project = null;

        java.io.File filePath = new java.io.File(filePathProject);

        try {
            FileInputStream fis;
            fis = new FileInputStream(filePath);
            ObjectInputStream is = new ObjectInputStream(fis);

            project = (Project) is.readObject();

            is.close();
            fis.close();

            if (project.getUser().getUsername().equals(user.getUsername()))
                throw new Exception("Invalid access: This file belongs from another user.");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return project;
    }
}