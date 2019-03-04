package com.example.mainpackage.logic.project.filemanagement.savefilepkg;

import com.example.mainpackage.logic.project.Project;
import com.example.mainpackage.logic.project.filemanagement.FileType;

public abstract class SaveProjectBuilder {

    String filePathString;
    protected Project project;

    public abstract boolean saveProject();

    public static SaveProjectBuilder getBuilder(FileType type) {
        if (type == FileType.BINARY)
            return new BinarySaveProjectBuilder();
        else if (type == FileType.BLIF)
            return new BlifSaveProjectBuilder();

        throw new IllegalStateException("Invalid type:" + type);
    }

    public void setFilePathString(String filePathString) {
        this.filePathString = filePathString;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
