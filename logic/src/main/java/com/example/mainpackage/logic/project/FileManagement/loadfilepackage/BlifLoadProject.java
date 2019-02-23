package com.example.mainpackage.logic.project.FileManagement.loadfilepackage;


import com.example.mainpackage.logic.project.FileManagement.File;
import com.example.mainpackage.logic.project.Project;
import com.example.mainpackage.logic.project.component.Component;
import com.example.mainpackage.logic.project.component.ComponentModule;
import com.example.mainpackage.logic.project.component.ComponentType;
import com.example.mainpackage.logic.user.User;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlifLoadProject {
    
    public Project loadBlifProject(String filePathString, User user){
        Project project;

        try {
            List<String> allLines = Files.readAllLines(Paths.get(filePathString));

            //TODO: need to improve this for android... need to know how is defined the filePath to get the project name...
            String projectName = filePathString.replace(".blif", ""); project = new Project(user, projectName);

            ComponentModule module = getModuleFromLines(allLines, user);

            project.setComponentModule(module);

            return project;


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private ComponentModule getModuleFromLines(List<String> allLines, User user) throws Exception {

        List<Component> data = new ArrayList<>();
        List<Component> outputList = new ArrayList<>();
        Project project;

        //get name of project
        String projectnametmp = allLines.get(0).replace(".model ","");
        project = new Project(user, projectnametmp);
        allLines.remove(0);

        //get inputs of project
        String inputsTmp = allLines.get(0).replace(".inputs ","");
        String[] inputNames = inputsTmp.split(" ");
        for(int i = 0; i < inputNames.length; i++){
            Component componentTmp = getComponentByName(inputNames[i]);
            data.add(componentTmp);
        }
        allLines.remove(0);

        //get inputs of project
        String ouputsTmp = allLines.get(0).replace(".outputs ","");
        String[] ouputNames = ouputsTmp.split(" ");
        for(int i = 0; i < ouputNames.length; i++){
            Component componentTmp = getComponentByName(ouputNames[i]);
            outputList.add(componentTmp);
        }
        allLines.remove(0);

        //process all components and their connections
        for (String line : allLines) {

            if (line.contains(".names")) {
                String namesTmp = line.replace(".names ", "");
                String[] componentNames = namesTmp.split(" ");
                List<Component> componentListTmp = new ArrayList<>();
                for (int i = 0; i < componentNames.length; i++) {
                    Component componentTmp = null;

                    //if exists is returned the component
                    componentTmp = verifyIfExistComponentWithThatName(data, componentNames[i]);

                    //if does not exist a component with that name we need to create it and add to data
                    if (componentTmp == null) {
                        componentTmp = getComponentByName(componentNames[i]);
                        data.add(componentTmp);
                    }
                    componentListTmp.add(componentTmp);
                }
                //remove and get last component
                Component lastComponent = componentListTmp.remove(componentListTmp.size() - 1);
                //to the last component define the others as previous elements
                for (Component component : componentListTmp)
                    lastComponent.setPrevious(component);
            } else if (line.contains(".end")) {
                ComponentModule componentModuleTemp = (ComponentModule) Component.getComponent(ComponentType.PROJECT, false, new int[]{0, 0});
                componentModuleTemp.addComponent(data);
                return componentModuleTemp;
            }
            else if(line.contains(".subckt")) {
                List<ComponentModule> listModulesTmp = new ArrayList<>();
                String namesTmp = line.replace(".subckt ", "");
                String[] components = namesTmp.split(" "); //omponentName1 is the name of the module... the others are components connections


                //LOAD THE COMPONENT
                String tempModuleName = components[0];
                List<String> linesOfTempModule = new ArrayList();
                //create the temp module
                boolean read = false;
                int i = allLines.indexOf(line); //to not add the line ".subckt" since it also has the component name
                i++;
                while (i < allLines.size()) {

                    if (allLines.get(i).contains(tempModuleName))
                        read = true;

                    if (read)
                        linesOfTempModule.add(allLines.get(i));

                    if (allLines.get(i).contains(".end") && read) //if the line contains ".end" and was reading (getting lines of a module)...
                    {
                        ComponentModule tmpModule = getModuleFromLines(linesOfTempModule, user);
                        listModulesTmp.add(tmpModule);
                        data.add(tmpModule);
                        break;
                    }
                    i++;
                }

                //PROCESS IT's CONNECTIONS
                for(int k = 1; k <components.length; k++){
                    String connection = new String(components[k]);
                    String[] connectionComponents = connection.split("="); //connection[0] is the previous;

                    if(!(connectionComponents.length==1)) // connectionComponents.length==1 when for example we have "output20=" ...
                    {
                        Component c1 = verifyIfExistComponentWithThatName(data, connectionComponents[0]);
                        Component c2 = verifyIfExistComponentWithThatName(data, connectionComponents[1]);
                        c1.setPrevious(c2);
                    }
                }
            }
        }

        return null;
    }


    private static Component verifyIfExistComponentWithThatName(List<Component> data, String componentName) {

        for(Component component : data){

            if(component instanceof ComponentModule) //if the component it's a component module we need to verify it's data
            {
                Component tmp = verifyIfExistComponentWithThatName(((ComponentModule) component).getData(), componentName);
                if(tmp != null) //if find a module with that component return...
                    return tmp;
            }
            else if(component.getName().equals(componentName))
                return component;
        }
        return null;
    }

    private static Component getComponentByName(String name) throws Exception {
        Component componentTmp = null;
        String componentName = name;
        String typeOfComponent = name.replaceAll("[0-9]", "");

        switch (typeOfComponent)
        {
            case "input":
                componentTmp = Component.getComponent(ComponentType.INPUT, false, new int[]{0, 0});
                break;
            case "output":
                componentTmp = Component.getComponent(ComponentType.OUTPUT, false, new int[]{0, 0});
                break;
            case "project":
                componentTmp = Component.getComponent(ComponentType.PROJECT, false, new int[]{0, 0});
                break;
            case "and":
                componentTmp = Component.getComponent(ComponentType.LOGIC_AND, false, new int[]{0, 0});
                break;
            case "or":
                componentTmp = Component.getComponent(ComponentType.LOGIC_OR, false, new int[]{0, 0});
                break;
            case "module":
                componentTmp = Component.getComponent(ComponentType.MODULE, false, new int[]{0, 0});
                break;
            default:
                throw new Exception("Invalid type: " + typeOfComponent + ".");

        }
        componentTmp.setName(componentName);
        return componentTmp;
    }
}
