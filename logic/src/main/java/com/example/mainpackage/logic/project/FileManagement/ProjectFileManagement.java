/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.mainpackage.logic.project.FileManagement;

import com.example.mainpackage.Config;
import com.example.mainpackage.logic.project.FileManagement.SaveFilePackage.SaveProjectBuilder;
import com.example.mainpackage.logic.project.FileManagement.loadfilepackage.BinaryLoadProjectAdapter;
import com.example.mainpackage.logic.project.FileManagement.loadfilepackage.BlifLoadProjectAdapter;
import com.example.mainpackage.logic.project.FileManagement.loadfilepackage.LoadProject;
import com.example.mainpackage.logic.project.Project;

import java.util.Arrays;
import java.util.List;

public class ProjectFileManagement {

    SaveProjectBuilder saveProjectBuilder;
    LoadProject loadProject;

    public ProjectFileManagement() {

    }

    public boolean saveProject(Project project, int fileType) throws Exception{
        boolean result;
        String fileName = project.getName();
        saveProjectBuilder = SaveProjectBuilder.getBuilder(fileType);
        result = saveProjectBuilder.saveProject(fileName, project);

        /*
        if(result)
            File.saveLastComponentNumber(project.getComponentModule().getUniqueNumber());
        */


        return result;
    }

    public Project loadProject(String fileName) throws Exception{
        int type = getProjectType(fileName);
        if(type == Config.FILE_TYPE_BINARY)
            this.loadProject = new BinaryLoadProjectAdapter();
        else if(type == Config.FILE_TYPE_BLIF)
            this.loadProject = new BlifLoadProjectAdapter();

        return loadProject.loadProject(fileName);
    }


    private int getProjectType(String fileName) throws Exception{
        List<String> items = null;

        try{

            items = Arrays.asList(fileName.split("\\."));
            String lastString = items.get(items.size()-1);

            if(lastString.equals("blif"))
                return Config.FILE_TYPE_BLIF;
            else if(lastString.equals("bin"))
                return Config.FILE_TYPE_BINARY;
            else
                throw new Exception(Config.ERROR_MSG_FILETYPE);

        }catch(Exception ex){
            throw new Exception(Config.ERROR_MSG_FILETYPE);
        }
    }
}