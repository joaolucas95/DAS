package com.example.mainpackage.logic.project.FileManagement;

import com.example.mainpackage.logic.project.FileManagement.SaveFilePackage.SaveProjectBuilder;
import com.example.mainpackage.logic.project.FileManagement.loadfilepackage.BinaryLoadProjectAdapter;
import com.example.mainpackage.logic.project.FileManagement.loadfilepackage.BlifLoadProjectAdapter;
import com.example.mainpackage.logic.project.FileManagement.loadfilepackage.LoadProject;
import com.example.mainpackage.logic.project.Project;
import com.example.mainpackage.logic.user.User;
import com.example.mainpackage.logic.utils.Config;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class ProjectFileManagement {
    
    private SaveProjectBuilder saveProjectBuilder;
    private LoadProject loadProject;
    
    public ProjectFileManagement() {
        // Do nothing
    }
    
    public boolean saveProject(Project project, String filePath, FileType fileType) {
        boolean result;
        saveProjectBuilder = SaveProjectBuilder.getBuilder(fileType);
        result = saveProjectBuilder.saveProject(filePath, project);

        //TODO: when export project to html?
        exportTestsToHtml(filePath, project);
        /*
        if(result)
            File.saveLastComponentNumber(project.getComponentModule().getUniqueNumber());
        */
        return result;
    }

    private void exportTestsToHtml(String filePath, Project project) {
        File.exportTestsToHtml(filePath, project);
    }

    public Project loadProject(String filePathProject, User user) throws Exception{
        FileType type = getProjectType(filePathProject);
        if(type == FileType.BINARY)
            this.loadProject = new BinaryLoadProjectAdapter();
        else if(type == FileType.BLIF)
            this.loadProject = new BlifLoadProjectAdapter();

        return loadProject.loadProject(filePathProject, user);
    }

    public void removeProject(String filePath) throws FileNotFoundException {
        File.removeProject(filePath);
    }
    
    private FileType getProjectType(String fileName) throws Exception{
        List<String> items;
        
        try{
            
            items = Arrays.asList(fileName.split("\\."));
            String lastString = items.get(items.size()-1);
            
            if(lastString.equals("blif"))
                return FileType.BLIF;
            else if(lastString.equals("bin"))
                return FileType.BINARY;
            else
                throw new Exception(Config.ERROR_MSG_FILETYPE);

        }catch(Exception ex){
            throw new Exception(Config.ERROR_MSG_FILETYPE);
        }
    }
}
