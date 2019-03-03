package com.example.mainpackage.logic.project.filemanagement.loadfilepkg;


import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;

import com.example.mainpackage.LogicFacadeImp;
import com.example.mainpackage.logic.dblogic.FileHistoryViewModel;
import com.example.mainpackage.logic.dblogic.FilePath;
import com.example.mainpackage.logic.project.Project;
import com.example.mainpackage.logic.project.component.Component;
import com.example.mainpackage.logic.project.component.ComponentInput;
import com.example.mainpackage.logic.project.component.ComponentModule;
import com.example.mainpackage.logic.project.component.ComponentType;
import com.example.mainpackage.logic.project.tests.Combination;
import com.example.mainpackage.logic.project.tests.Signal;
import com.example.mainpackage.logic.project.tests.Test;
import com.example.mainpackage.logic.user.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BlifLoadProject {
    
    public Project loadBlifProject(String filePathProject, User user){
        Project project;

        try {
            List<String> allLines  = new ArrayList<>();

            File file = new File(filePathProject);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                allLines.add(line);
            }

            String[] filePathByPath = filePathProject.split("/");
            String projectName= filePathByPath[filePathByPath.length-1];
            projectName = projectName.replace(".blif", "");

            String moduleLine = allLines.get(0).replace(".model ","");
            //modulestr[0] - is the name of the module ; modulestr[1] - are the coords of the component module
            String[] modulestr = moduleLine.split("-");
            ComponentModule module = getModuleFromLines(allLines);

            Signal signalForSimulation = getSignalForSimulation(allLines);

            List<Test> tests = getTestsOfProject(allLines);

            project = new Project(user, projectName);
            project.setComponentModule(module);
            project.setSignalForSimulation(signalForSimulation);
            project.setTests(tests);
            return project;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<Test> getTestsOfProject(List<String> allLines) {
        List<Test> tests = new ArrayList<>();

        for(String line : allLines)
        {
            if(line.contains(".test")){
                Signal inputSignal = new Signal(), expectedSignal = new Signal();

                String lineTmp = line.replace(".test ", "");

                String[] inputsAndExpected = lineTmp.split("/");

                //process inputs
                String[] valuesStr = inputsAndExpected[0].split(" ");
                Combination combination = getCombination(valuesStr);

                if(combination != null)
                    inputSignal.getCombinations().add(combination);

                //process expected
                valuesStr = inputsAndExpected[1].split(" ");
                combination = getCombination(valuesStr);

                if(combination != null)
                    expectedSignal.getCombinations().add(combination);
                tests.add(new Test(inputSignal,expectedSignal));
            }
        }

        return tests;
    }

    private Signal getSignalForSimulation(List<String> allLines) throws Exception {
        Signal signalTmp = new Signal();
        for(String line : allLines)
        {
            if(line.contains(".simulation")){
                String lineTmp = line.replace(".simulation ", "");

                String[] combinationsStr = lineTmp.split(";");
                for(String combinationStr : combinationsStr){
                    String[] valuesStr = combinationStr.split(" ");

                    Combination combination = getCombination(valuesStr);

                    if(combination != null && !combination.getValues().isEmpty())
                        signalTmp.getCombinations().add(combination);
                }
            }
        }
        return signalTmp;
    }

    private Combination getCombination(String[] valuesStr) {
        boolean result;

        Map<String, Boolean> values = new LinkedHashMap<>();

        if(valuesStr.length == 0)
            return null;


        for(int i = 0 ; i<valuesStr.length ; i++){
            String[] combinationTmp = valuesStr[i].split("=");
            if(combinationTmp.length==2)
            {
                result = combinationTmp[1].equals("true")? true : false;
                values.put(combinationTmp[0], result);
            }
        }
        return new Combination(values);
    }


    private ComponentModule getModuleFromLines(List<String> allLines) throws Exception {

        List<Component> data = new ArrayList<>();

        //get information of the MODULE

        //get the line which has the name and positions of the component module
        String moduleLine = allLines.get(0).replace(".model ","");
        //modulestr[0] - is the name of the module ; modulestr[1] - are the coords of the component module
        String[] modulestr = moduleLine.split("-");
        //define coords
        String[] coordsTmp= modulestr[1].split(";");
        int[] positionsOfModule = new int[]{Integer.parseInt(coordsTmp[0]), Integer.parseInt(coordsTmp[1])};
        String moduleName = modulestr[0];

        //--------------- module defined (project name will be used to define the projects name
        // now get content to the module
        //...

        allLines.remove(0);

        //get inputs of the module
        String inputsTmp = allLines.get(0).replace(".inputs ","");
        String[] inputNames = inputsTmp.split(" ");
        for(int i = 0; i < inputNames.length; i++){
            //input[0] - is the name of the input ; input[1] - are the coords of the component input
            String[] input = inputNames[i].split("-");

            //define coords
            String[] coords= input[1].split(";");
            int[] position = new int[]{Integer.parseInt(coords[0]), Integer.parseInt(coords[1])};

            //define the component with the name and the coords
            Component componentTmp = getComponentByName(input[0], position);

            data.add(componentTmp);
        }

        allLines.remove(0);

        //remove the line which defines the outputs... The outputs will be added
        allLines.remove(0);

        //process all components and their connections
        for (String line : allLines) {

            if (line.contains(".names")) {
                String namesTmp = line.replace(".names ", "");
                String[] componentNames = namesTmp.split(" ");
                List<Component> componentListTmp = new ArrayList<>();
                for (int i = 0; i < componentNames.length; i++) {
                    Component componentTmp = null;

                    //strComponent[0] - is the name of the output ; strComponent[1] - are the coords of the component output
                    String[] strComponent = componentNames[i].split("-");

                    //define coords
                    String[] coords= strComponent[1].split(";");
                    int[] position = new int[]{Integer.parseInt(coords[0]), Integer.parseInt(coords[1])};


                    //if exists is returned the component
                    componentTmp = verifyIfExistComponentWithThatName(data, strComponent[0]);

                    //if does not exist a component with that name we need to create it and add to data
                    if (componentTmp == null) {

                        //define the component with the name and the coords
                        componentTmp = getComponentByName(strComponent[0], position);

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
                ComponentModule componentModuleTemp = (ComponentModule) Component.getComponent(ComponentType.PROJECT, false, positionsOfModule);
                componentModuleTemp.addComponent(data);
                componentModuleTemp.setName(moduleName);
                return componentModuleTemp;
            }
            else if(line.contains(".subckt")) {
                List<ComponentModule> listModulesTmp = new ArrayList<>();
                String namesTmp = line.replace(".subckt ", "");
                String[] components = namesTmp.split(" "); //omponentName1 is the name of the module... the others are components connections

                //LOAD THE COMPONENT

                //strComponent[0] - is the name of the module ; strComponent[1] - are the coords of the component module
                String[] strComponent = components[0].split("-");

                String tempModuleName = strComponent[0];

                List<String> linesOfTempModule = new ArrayList<>();
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
                        ComponentModule tmpModule = getModuleFromLines(linesOfTempModule); //new String because it's not relevant... We used this var to get the name of the mainModule... used to define the project name
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

    private static Component getComponentByName(String name, int[] position) throws Exception {
        Component componentTmp = null;
        String componentName = name;
        String typeOfComponent = name.replaceAll("[0-9]", "");

        switch (typeOfComponent)
        {
            case "input":
                componentTmp = Component.getComponent(ComponentType.INPUT, false, position);
                break;
            case "output":
                componentTmp = Component.getComponent(ComponentType.OUTPUT, false, position);
                break;
            case "project":
                componentTmp = Component.getComponent(ComponentType.PROJECT, false, position);
                break;
            case "and":
                componentTmp = Component.getComponent(ComponentType.LOGIC_AND, false, position);
                break;
            case "or":
                componentTmp = Component.getComponent(ComponentType.LOGIC_OR, false, position);
                break;
            case "module":
                componentTmp = Component.getComponent(ComponentType.MODULE, false, position);
                break;
            default:
                throw new Exception("Invalid type: " + typeOfComponent + ".");

        }
        componentTmp.setName(componentName);
        return componentTmp;
    }
}
