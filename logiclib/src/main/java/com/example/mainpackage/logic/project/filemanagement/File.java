package com.example.mainpackage.logic.project.filemanagement;

import com.example.mainpackage.logic.project.tests.Combination;
import com.example.mainpackage.logic.project.Project;
import com.example.mainpackage.logic.project.tests.Test;
import com.example.mainpackage.logic.utils.Config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class File {

    private java.io.File file;

    public static boolean saveLastComponentNumber(int number) {

        //String filePathString = context.getFilesDir().getPath().toString() + "/" + FILE_NAME;

        String filePathString = Config.LAST_COMPONENT_NUMBER_FILENAME;

        java.io.File file = new java.io.File(filePathString);

        FileOutputStream fos;
        ObjectOutputStream os;

        try {
            if (!file.exists() && !file.createNewFile()) {
                throw new IllegalStateException("Failed to create new file");
            }

            fos = new FileOutputStream(file);
            os = new ObjectOutputStream(fos);
            os.writeObject(number);
            os.close();
            fos.close();

        } catch (IOException e) {
            System.out.println(Config.ERROR_MSG_FILE_LAST_COMPONENT_NUMBER);
        }

        return true;
    }

    public static int loadLasComponentNumber() {
        int number = 0;

        String filePathString = Config.LAST_COMPONENT_NUMBER_FILENAME;
        java.io.File filePath = new java.io.File(filePathString);

        try {
            FileInputStream fis;
            fis = new FileInputStream(filePath);
            ObjectInputStream is = new ObjectInputStream(fis);

            number = (int) is.readObject();

            is.close();
            fis.close();
        } catch (Exception e) {
            //TODO: and if can't find the file and exist projects?
        }

        return number;
    }

    public static void removeProject(String filePath) throws FileNotFoundException {

        String htmlPath = new String(filePath);

        if(filePath.contains(".blif"))
            htmlPath = htmlPath.replace(".blif", ".html");
        else if(filePath.contains(".bin"))
            htmlPath = htmlPath.replace(".bin", ".html");

        //remove project file
        java.io.File file = new java.io.File(filePath);
        file.delete();

        //remove html file
        file = new java.io.File(htmlPath);
        file.delete();


    }
}
