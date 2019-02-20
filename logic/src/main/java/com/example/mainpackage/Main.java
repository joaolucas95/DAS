package com.example.mainpackage;

import com.example.mainpackage.logic.project.Combination;
import com.example.mainpackage.logic.project.CommandAddComponent;
import com.example.mainpackage.logic.project.CommandConnectComponent;
import com.example.mainpackage.logic.project.CommandManager;
import com.example.mainpackage.logic.project.ComponentBuilder;
import com.example.mainpackage.logic.project.FileManagement.ProjectFileManagement;
import com.example.mainpackage.logic.project.Project;
import com.example.mainpackage.logic.project.Signal;
import com.example.mainpackage.logic.project.Test;
import com.example.mainpackage.logic.project.component.Component;
import com.example.mainpackage.logic.project.component.ComponentType;
import com.example.mainpackage.logic.statemachinepackage.ComponentEditorStateMachine;
import com.example.mainpackage.logic.user.User;
import com.example.mainpackage.logic.utils.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    
    public static void main(String[] args) {   
        //printTestA(false,true,true); //testing the module TestA
        //printTestB(true, true, false, false); //testing the global project
        //printTestB(true, true, false, false); //testing the global project
        
        //User for tests
        User.getInstance().setUsername("Joaquim");

        Project projectTestA = printTestAWithStateMachine(User.getInstance());
        printTestASimulations(projectTestA);
        printTestATests(projectTestA);
        if(saveProject(projectTestA, Config.FILE_TYPE_BINARY))
            System.out.println("Project saved with success.");
        else
            System.out.println("Error saving the project: " + projectTestA);


        Project projectTestB = printTestBWithStateMachine(User.getInstance());
        printTestBSimulations(projectTestB);
        printTestBTests(projectTestB);
        if(saveProject(projectTestB, Config.FILE_TYPE_BINARY))
            System.out.println("Project saved with success.");
        else
            System.out.println("Error saving the project: " + projectTestB);/*
 */


        //   printTestSavingProjectBinaryFile();
    }
    
    private static Project printTestAWithCommands(User user) {
        boolean result;
        String projectName = "modelTest";
        
        Component model = createModelTestWithCommands();
        
        Project project = new Project(user, projectName);
        project.setComponentModule(model);
        
        try {
            ProjectFileManagement projectFileManagement = new ProjectFileManagement();
            result = projectFileManagement.saveProject(project, Config.FILE_TYPE_BINARY);
            
            if(result)
                System.out.println("Project saved with success.");
            else
                System.out.println("Error saving the project: " + project);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return project;
    }
    
    private static Component createModelTestWithCommands() {
        CommandAddComponent cmAddComponent;
        CommandConnectComponent cmConnectComponent;
        
        ComponentBuilder componentBuilder =new ComponentBuilder();
        CommandManager commandManager = new CommandManager(componentBuilder);
        
        cmAddComponent = new CommandAddComponent(ComponentType.INPUT);
        commandManager.apply(cmAddComponent);

        cmAddComponent = new CommandAddComponent(ComponentType.INPUT);
        commandManager.apply(cmAddComponent);

        cmAddComponent = new CommandAddComponent(ComponentType.INPUT);
        commandManager.apply(cmAddComponent);

        //commandManager.undo();
        //commandManager.redo();


        cmAddComponent = new CommandAddComponent(ComponentType.LOGIC_AND);
        commandManager.apply(cmAddComponent);
        
        cmConnectComponent = new CommandConnectComponent("input1", "and4");
        commandManager.apply(cmConnectComponent);
        cmConnectComponent = new CommandConnectComponent("input2", "and4");
        commandManager.apply(cmConnectComponent);
        

        cmAddComponent = new CommandAddComponent(ComponentType.LOGIC_AND);
        commandManager.apply(cmAddComponent);
        
        cmConnectComponent = new CommandConnectComponent("input2", "and5");
        commandManager.apply(cmConnectComponent);
        cmConnectComponent = new CommandConnectComponent("input3", "and5");
        commandManager.apply(cmConnectComponent);
        
        cmAddComponent = new CommandAddComponent(ComponentType.LOGIC_OR);
        commandManager.apply(cmAddComponent);
        cmConnectComponent = new CommandConnectComponent("and4", "or6");
        commandManager.apply(cmConnectComponent);
        cmConnectComponent = new CommandConnectComponent("and5", "or6");
        commandManager.apply(cmConnectComponent);
        
        
        cmAddComponent = new CommandAddComponent(ComponentType.OUTPUT);
        commandManager.apply(cmAddComponent);
        
        cmConnectComponent = new CommandConnectComponent("and4", "output7");
        commandManager.apply(cmConnectComponent);
        
        cmAddComponent = new CommandAddComponent(ComponentType.OUTPUT);
        commandManager.apply(cmAddComponent);
        
        cmConnectComponent = new CommandConnectComponent("or6", "output8");
        commandManager.apply(cmConnectComponent);
        
        //test undo and redo of a connection
        //commandManager.undo();
        //commandManager.redo();
        
        return componentBuilder.build();
    }
    
    private static Project printTestAWithStateMachine(User user) {
        String projectName = "modelTest";
        
        Component model = createModelTestWithStateMachine();
        
        Project project = new Project(user, projectName);
        project.setComponentModule(model);

        return project;
    }

    private static boolean saveProject(Project project, int fileType){
        boolean result;
        try {
            ProjectFileManagement projectFileManagement = new ProjectFileManagement();
            result = projectFileManagement.saveProject(project, fileType);

            return result;

        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    private static Component createModelTestWithStateMachine(){
        ComponentEditorStateMachine stateMachine = new ComponentEditorStateMachine(ComponentType.MODULE);
        stateMachine.addSimpleComponent(ComponentType.INPUT);
        stateMachine.addSimpleComponent(ComponentType.INPUT);
        stateMachine.addSimpleComponent(ComponentType.INPUT);
        
        stateMachine.addSimpleComponent(ComponentType.LOGIC_AND);
        
        //test
        stateMachine.selectComponent("and4");
        stateMachine.selectComponent("and4");
        
        stateMachine.selectComponent("input1");
        stateMachine.selectComponent("and4");
        stateMachine.selectComponent("input2");
        stateMachine.selectComponent("and4");
        
        stateMachine.addSimpleComponent(ComponentType.LOGIC_AND);
        stateMachine.selectComponent("input2");
        stateMachine.selectComponent("and5");
        stateMachine.selectComponent("input3");
        stateMachine.selectComponent("and5");
        
        stateMachine.addSimpleComponent(ComponentType.LOGIC_OR);
        stateMachine.selectComponent("and4");
        stateMachine.selectComponent("or6");
        stateMachine.selectComponent("and5");
        stateMachine.selectComponent("or6");
        
        stateMachine.addSimpleComponent(ComponentType.OUTPUT);
        stateMachine.selectComponent("and4");
        stateMachine.selectComponent("output7");
        
        stateMachine.addSimpleComponent(ComponentType.OUTPUT);
        stateMachine.selectComponent("or6");
        stateMachine.selectComponent("output8");
        
        return stateMachine.finishComponentEditor();
    }
    
    private static Project printTestBWithStateMachine(User user) {
        String projectName = "modelGlobalTest";
        
        Component model = createGlobalModuleTestWithStateMachine(user);
        
        Project project = new Project(user,projectName);
        project.setComponentModule(model);

        return project;
    }
    
    private static Component createGlobalModuleTestWithStateMachine(User user){
        ComponentEditorStateMachine stateMachine = new ComponentEditorStateMachine(ComponentType.PROJECT);
        stateMachine.addSimpleComponent(ComponentType.INPUT);
        stateMachine.addSimpleComponent(ComponentType.INPUT);
        stateMachine.addSimpleComponent(ComponentType.INPUT);
        stateMachine.addSimpleComponent(ComponentType.INPUT);

        //test
        stateMachine.addSimpleComponent(ComponentType.LOGIC_AND);
   
        stateMachine.addModule("modelTest.bin", user);

        stateMachine.selectComponent("input10");
        stateMachine.selectComponent("input14");
        
        stateMachine.selectComponent("input11");
        stateMachine.selectComponent("input15");
        
        stateMachine.selectComponent("input12");
        stateMachine.selectComponent("input16");
        
        stateMachine.addModule("modelTest.bin", user);
        
        stateMachine.selectComponent("output21");
        stateMachine.selectComponent("input22");
        
        stateMachine.selectComponent("input12");
        stateMachine.selectComponent("input23");
        
        stateMachine.selectComponent("input13");
        stateMachine.selectComponent("input24");
        
        stateMachine.addSimpleComponent(ComponentType.OUTPUT);

        stateMachine.selectComponent("output29");
        stateMachine.selectComponent("output30");
        
        return stateMachine.finishComponentEditor();
    }

    private static void printTestASimulations(Project project) {
        List<Combination> combinations = new ArrayList();
        Map<String, Boolean> testtmp = new HashMap<>();
        
        testtmp.put("input1", false);
        testtmp.put("input2", false);
        testtmp.put("input3", false);
        combinations.add(new Combination(testtmp));
        
        testtmp = new HashMap<>();
        testtmp.put("input1", true);
        testtmp.put("input2", true);
        testtmp.put("input3", false);
        combinations.add(new Combination(testtmp));
        
        testtmp = new HashMap<>();
        testtmp.put("input1", false);
        testtmp.put("input2", true);
        testtmp.put("input3", true);
        combinations.add(new Combination(testtmp));
        
        Signal signal = new Signal(combinations);
        
        project.setSignalForSimulation(signal);
        
        runSimulation(project);

    }
    
    private static void printTestBSimulations(Project project) {
        List<Combination> combinations = new ArrayList();
        Map<String, Boolean> testtmp = new HashMap<>();
        
        testtmp.put("input10", false);
        testtmp.put("input11", false);
        testtmp.put("input12", false);
        testtmp.put("input13", false);

        combinations.add(new Combination(testtmp));
        
        testtmp = new HashMap<>();
        testtmp.put("input10", false);
        testtmp.put("input11", true);
        testtmp.put("input12", true);
        testtmp.put("input13", false);
        combinations.add(new Combination(testtmp));
        
        testtmp = new HashMap<>();
        testtmp.put("input10", true);
        testtmp.put("input11", true);
        testtmp.put("input12", false);
        testtmp.put("input13", false);
        combinations.add(new Combination(testtmp));
        
        Signal signal = new Signal(combinations);
        project.setSignalForSimulation(signal);
        
        runSimulation(project);
    }

    private static void runSimulation(Project project) {
        List<Combination> results = project.runSimulation();

        System.out.println("Simulations for " + project.getName() + ":");        
        for(int i = 0; i< project.getSignalForSimulation().getCombinations().size(); i++)
        {
            Combination combinationInput = project.getSignalForSimulation().getCombinations().get(i);
            Combination result = results.get(i);
            
            System.out.println("Inputs:");
            Iterator it = combinationInput.getValues().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();                
                System.out.println("" + (String) pair.getKey() + ": " + pair.getValue());
            }
            System.out.println("Result:");
            it = result.getValues().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();                
                System.out.println("" + (String) pair.getKey() + ": " + pair.getValue());
            }  
            System.out.println("");
        }
    }

    private static void printTestATests(Project project) {
        List<Combination> combinationsInput = new ArrayList();
        List<Combination> combinationsExpected = new ArrayList();

        Map<String, Boolean> testtmp;
        
        testtmp = new HashMap<>();
        testtmp.put("input1", true);
        testtmp.put("input2", true);
        testtmp.put("input3", false);
        combinationsInput.add(new Combination(testtmp));
                
        testtmp = new HashMap<>();
        testtmp.put("input1", false);
        testtmp.put("input2", true);
        testtmp.put("input3", true);
        combinationsInput.add(new Combination(testtmp));
        
        Signal signalInput = new Signal(combinationsInput);
        
        testtmp = new HashMap<>();
        testtmp.put("output7", true);
        testtmp.put("output8", true);
        combinationsExpected.add(new Combination(testtmp));
        
        testtmp = new HashMap<>();
        testtmp.put("output7", false);
        testtmp.put("output8", true);
        combinationsExpected.add(new Combination(testtmp));
        
        
        Signal signalExpected = new Signal(combinationsExpected);

        Test test = new Test(signalInput, signalExpected);
        
        project.addTest(test);
        runTests(project);
    }

    private static void printTestBTests(Project project) {
        List<Combination> combinationsInput = new ArrayList();
        List<Combination> combinationsExpected = new ArrayList();

        Map<String, Boolean> testtmp;
        
        testtmp = new HashMap<>();
        testtmp.put("input10", false);
        testtmp.put("input11", true);
        testtmp.put("input12", true);
        testtmp.put("input13", false);
        combinationsInput.add(new Combination(testtmp));
                
        testtmp = new HashMap<>();
        testtmp.put("input10", true);
        testtmp.put("input11", true);
        testtmp.put("input12", false);
        testtmp.put("input13", false);
        combinationsInput.add(new Combination(testtmp));
        
        Signal signalInput = new Signal(combinationsInput);
        
        testtmp = new HashMap<>();
        testtmp.put("output30", true);
        combinationsExpected.add(new Combination(testtmp));
        
        testtmp = new HashMap<>();
        testtmp.put("output30", false);
        combinationsExpected.add(new Combination(testtmp));
        
        
        Signal signalExpected = new Signal(combinationsExpected);

        Test test = new Test(signalInput, signalExpected);
        
        project.addTest(test);
        runTests(project);
    }
    
    private static void runTests(Project project) {
        System.out.println("Running Test...");
        System.out.println("Project Name: " + project.getName());
        for(Test test : project.getTests())
        {
            System.out.println("Input Signal: " + test.getSignalInput());
            System.out.println("Expected Signal: " + test.getSignalExpected());
            System.out.println("Result:" + project.runTest(test));
        }
        
        System.out.println("\n\n");
    }

    

}
