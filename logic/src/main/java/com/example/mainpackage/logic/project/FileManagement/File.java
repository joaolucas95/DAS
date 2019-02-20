package com.example.mainpackage.logic.project.FileManagement;

import com.example.mainpackage.logic.project.Combination;
import com.example.mainpackage.logic.project.Project;
import com.example.mainpackage.logic.project.Test;
import com.example.mainpackage.logic.project.component.Component;
import com.example.mainpackage.logic.project.component.ComponentInput;
import com.example.mainpackage.logic.project.component.ComponentModule;
import com.example.mainpackage.logic.project.component.ComponentSimple;
import com.example.mainpackage.logic.user.User;
import com.example.mainpackage.logic.utils.Config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class File {
    
    private java.io.File file;

    public static boolean saveProjectAsBinFile(String filePathString, Project project) {

        //String filePathString = context.getFilesDir().getPath().toString() + "/" + FILE_NAME;
        
        filePathString += project.getName() + ".bin";

        java.io.File filePath = new java.io.File(filePathString);

        FileOutputStream fos = null;
        ObjectOutputStream os = null;

        try {
            fos = new FileOutputStream(filePath);
            os = new ObjectOutputStream(fos);
            os.writeObject(project);
            os.close();
            fos.close();

            //System.out.println("Project saved.");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    public static boolean saveProjectAsBlifFile(String filePathString, Project project) {
        
        try {
            List<ComponentInput> inputTempTempList = new ArrayList();
            
            List<String> content = new ArrayList();
            content.add(".content " + project.getName());
            
            String inputsString = ".inputs";
            for(Component input : project.getInputs())
                inputsString += " " + input.getName();
            content.add(inputsString);
            
            String outputsString = ".ouputs";
            for(Component output : project.getOutputs())
                outputsString += " " + output.getName();
            content.add(outputsString);
            
            String connection ="\n";

            ComponentModule module = (ComponentModule) project.getComponentModule();
            for(Component component : module.getData())
            {
                //if not input...
                if(!(component instanceof ComponentInput))
                {
                    connection += ".names";
                    String results ="";
                    
                    List<Component> previousList;
                    
                    //if the component is a componentModule the previous components are their output elements... So we need get his previous list by his inputs
                    if(component instanceof ComponentModule)
                    {
                        previousList = new ArrayList();
                        for(Component componenttmp : ((ComponentModule)component).getInputList())
                        {
                            previousList.addAll(componenttmp.getPrevious());
                        }
                    }
                    else
                    {
                        previousList = component.getPrevious();
                    }
                    for(Component previous : previousList)
                        connection += " " + previous.getName();
                    
                    connection += " " + component.getName() + "\n";
                    connection += ((ComponentSimple)component).getLogicGates();
                }
            }

            content.add(connection);                      
            content.add(".end");
            Path file = Paths.get(project.getName() + ".blif");
            Files.write(file, content, Charset.forName("UTF-8"));
        } catch (IOException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        return true;
    }

    public static Project loadProjectFromBlifFile(String filePathString, String projectName, User user){
        Project project = new Project(user, projectName);

        String filePathStringWithProjectName = filePathString + projectName;
        try {
            List<String> allLines = Files.readAllLines(Paths.get(filePathStringWithProjectName));

            String projectnametmp = allLines.get(0).replace(".model ","");

            allLines.remove(0);

            String inputsTmp = allLines.get(0).replace(".inputs ","");

            String[] inputNames = inputsTmp.split(" ");

            for(int i = 0; i< inputNames.length; i++){
                Component componentTmp;

                String componentName = inputNames[i];
                String typeOfComponent = inputNames[i].replaceAll("[0-9]", "");

                switch (typeOfComponent)
                {
                    case "input":
                        componentTmp = new ComponentInput(componentName);
                        break;
                }
            }


            for (String line : allLines) {

                if(line.contains(".content"))
                {

                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return project;
    }
    
    public static Project loadProjectFromBinFile(String filePathString, String projectName, User user) {
        Project project = null;
        
        //String filePathString = context.getFilesDir().getPath().toString() + "/" + FILE_NAME;
        
        filePathString += projectName;
        java.io.File filePath = new java.io.File(filePathString);


        try {
            FileInputStream fis = null;
            fis = new FileInputStream(filePath);
            ObjectInputStream is = new ObjectInputStream(fis);

            project = (Project) is.readObject();

            is.close();
            fis.close();


            if(project.getUser().getUsername() == user.getUsername())
                throw new Exception("Invalid access: This file belongs from another user.");

        } catch(Exception e) {
            e.printStackTrace();
        }
        return project;
    }
    
    
    public static boolean saveLastComponentNumber(int number) {

        //String filePathString = context.getFilesDir().getPath().toString() + "/" + FILE_NAME;
        
        String filePathString = Config.LAST_COMPONENT_NUMBER_FILENAME;

        java.io.File filePath = new java.io.File(filePathString);

        FileOutputStream fos = null;
        ObjectOutputStream os = null;

        try {
            fos = new FileOutputStream(filePath);
            os = new ObjectOutputStream(fos);
            os.writeObject(number);
            os.close();
            fos.close();

        } catch (Exception e) {
            System.out.println(Config.ERROR_MSG_FILE_LAST_COMPONENT_NUMBER);
        }
        
        return true;
    }
    
    public static int loadLasComponentNumber() {
        int number = 0;
        
        String filePathString = Config.LAST_COMPONENT_NUMBER_FILENAME;
        java.io.File filePath = new java.io.File(filePathString);
        
        try {
            FileInputStream fis = null;
            fis = new FileInputStream(filePath);
            ObjectInputStream is = new ObjectInputStream(fis);

            number = (int) is.readObject();

            is.close();
            fis.close();
        } catch(Exception e) {
            //TODO: and if can't find the file and exist projects?
        }

        return number;
    }

    public static boolean exportTestsToHtml(String filePath, Project project) {
        try {

            List<ComponentInput> inputTempTempList = new ArrayList();

            List<String> content = new ArrayList();
            content.add("<!DOCTYPE html><html>");
            content.add("<head>");
            content.add("<title>" + project.getName() +" </title>");
            content.add("<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css'>\n" +
                    "  <script src='https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js'></script>\n" +
                    "  <script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js'></script>");
            content.add("</head>");
            content.add("<body>");
            content.add("<div class='container'>");
            content.add("<h3>Running Test...</h3>");
            content.add("<div class='panel panel-default'>");

            content.add("<div class='panel-heading'>Project Name: " + project.getName() + "</div>");
            content.add("<div class='panel-body'>");

            for(Test test : project.getTests())
            {

                content.add("<b>Input Signal:</b> " + printStringCombinationHtml(test.getSignalInput().getCombinations()) + "</br>");
                content.add("<b>Expected Signal:</b> " + printStringCombinationHtml(test.getSignalExpected().getCombinations())+ " </br>");
                content.add("<b>Result:</b> " + project.runTest(test) + "</br>");
            }

            content.add("</div>");
            content.add("</div>");
            content.add("</div>");

            content.add("</body></html>");

            Path file = Paths.get(project.getName() + ".html");
            Files.write(file, content, Charset.forName("UTF-8"));
        } catch (IOException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
    }

    private static String printStringCombinationHtml(List<Combination> combinations){
        String str = "";

        for(Combination combination : combinations){

            str +="<br>Combination:<br>";
            //define inputs to module
            Iterator it = combination.getValues().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                str +=" " + (String)pair.getKey() +" - " + (boolean)pair.getValue() + "<br>";
            }
        }
        return str;
    }
}
