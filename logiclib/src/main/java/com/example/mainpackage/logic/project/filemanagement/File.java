package com.example.mainpackage.logic.project.filemanagement;

import com.example.mainpackage.logic.utils.Config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class File {

    public static void saveLastComponentNumber(int number) {

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

    public static void removeProject(String filePath) {
        String htmlPath = filePath;

        if (filePath.contains(".blif"))
            htmlPath = htmlPath.replace(".blif", ".html");
        else if (filePath.contains(".bin"))
            htmlPath = htmlPath.replace(".bin", ".html");

        //remove project file
        java.io.File file = new java.io.File(filePath);
        file.delete();

        //remove html file
        file = new java.io.File(htmlPath);
        file.delete();


    }
}
