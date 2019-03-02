package com.example.mainpackage.logic.project.tests.export;

import com.example.mainpackage.logic.project.Project;

public abstract class ExportTestBuilder {
    protected Project project;
    protected String filePath;

    public abstract boolean export();

    public static ExportTestBuilder getBuilder(ExportType type) {
        if (type == ExportType.HTML) {
            return new HtmlExportTestsBuilder();
        } else {
            throw new IllegalStateException("Invalid type:" + type);
        }
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
