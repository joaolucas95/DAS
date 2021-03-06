package com.example.mainpackage.logic.project.filemanagement.savefilepkg;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class BinarySaveProjectBuilder extends SaveProjectBuilder {

    @Override
    public boolean saveProject() {
        filePathString += "/" + project.getName() + ".bin";

        java.io.File filePath = new java.io.File(filePathString);

        FileOutputStream fos;
        ObjectOutputStream os;

        try {
            fos = new FileOutputStream(filePath);
            os = new ObjectOutputStream(fos);
            os.writeObject(project);
            os.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
