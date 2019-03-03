package com.example.mainpackage.logic.project.filemanagement;

import com.example.mainpackage.logic.project.filemanagement.savefilepkg.SaveProjectBuilder;
import com.example.mainpackage.logic.project.filemanagement.loadfilepkg.BlifLoadProjectAdapter;
import com.example.mainpackage.logic.project.filemanagement.loadfilepkg.LoadProject;
import com.example.mainpackage.logic.project.Project;
import com.example.mainpackage.logic.project.tests.export.ExportTestBuilder;
import com.example.mainpackage.logic.project.tests.export.ExportType;
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
        saveProjectBuilder.setFilePathString(filePath);
        saveProjectBuilder.setProject(project);
        result = saveProjectBuilder.saveProject();

        exportTestsToHtml(filePath, project);

        return result;
    }

    private void exportTestsToHtml(String filePath, Project project) {
        ExportTestBuilder builder = ExportTestBuilder.getBuilder(ExportType.HTML);
        builder.setFilePath(filePath);
        builder.setProject(project);
        builder.export();
    }

    public Project loadProject(String filePathProject, User user) throws Exception{
        FileType type = getProjectType(filePathProject);
        if(type == FileType.BINARY)
            this.loadProject = new LoadProject();
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
