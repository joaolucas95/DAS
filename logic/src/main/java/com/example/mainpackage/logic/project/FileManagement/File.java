package com.example.mainpackage.logic.project.FileManagement;

import com.example.mainpackage.logic.project.Combination;
import com.example.mainpackage.logic.project.Project;
import com.example.mainpackage.logic.project.Test;
import com.example.mainpackage.logic.project.component.Component;
import com.example.mainpackage.logic.project.component.ComponentInput;
import com.example.mainpackage.logic.project.component.ComponentModule;
import com.example.mainpackage.logic.utils.Config;

import java.io.FileInputStream;
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class File {
    
    private java.io.File file;

    public static boolean saveProjectAsBinFile(String filePathString, String projectName, Project project) {

        //String filePathString = context.getFilesDir().getPath().toString() + "/" + FILE_NAME;
        
        filePathString += projectName;

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
    
    public static boolean saveProjectAsBlifFile(String filePathString, String projectName, Project project) {
        
        try {
            
            List<ComponentInput> inputTempTempList = new ArrayList();
            
            List<String> content = new ArrayList();
            content.add(".content " + projectName);
            
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
                    
                    
                    // 1- criar uma lista de arrays dinamicamente com todas as posições possíveis; ex: 2 inputs; 0 0 ; 0 1 ; 1 0 ; 1 1
                    // 2- obter o resultado do component para cada elemento do array anterior... e ver quando é que dá true
                    //  2.a - se apenas houver um resultado true e' esse q e' escrito
                    //  2.b - se houver mais que um true fazer a devida comparacao para preencher devidamente
                    
                    /*
                    for(Component input : module.getInputList())
                        module.setInput(input.getName(), false);
                    
                    for(int i = 0; i < module.getInputList().size(); i++)
                    {
                        connection += "Input list: ";
                        for(Component caux : module.getInputList())
                            connection += " " + ((ComponentInput)caux).getOutput(caux.getName());
                        
                        results = " ----" + component.getOutput(component.getName()) + "\n";
                        String inputNameToChange = module.getInputList().get(i).getName();
                        module.setInput(inputNameToChange, true);
                        connection += results;
                    }
                    */
                    
                }
            }
            
            connection += "\n";
            content.add(connection);                      
            content.add(".end");
            Path file = Paths.get(projectName + ".blif");
            Files.write(file, content, Charset.forName("UTF-8"));
        } catch (IOException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        return true;
    }

    
    public static Project loadProject(String filePathString, String projectName) {
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
    
    public static Component getModuleByName(String filePathString, String projectName) {
        Project project = null;
        
        //String filePathString = context.getFilesDir().getPath().toString() + "/" + FILE_NAME;
        
        filePathString += projectName +".bin";
        java.io.File filePath = new java.io.File(filePathString);


        try {
            FileInputStream fis = null;
            fis = new FileInputStream(filePath);
            ObjectInputStream is = new ObjectInputStream(fis);

            project = (Project) is.readObject();

            is.close();
            fis.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return project.getComponentModule();
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
