package com.example.mainpackage.logic.project;

import com.example.mainpackage.logic.project.component.Component;
import com.example.mainpackage.logic.project.component.ComponentModule;
import com.example.mainpackage.logic.user.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Project implements Serializable{
    
    private Component componentModule;
    private User user;
    private String name;
    private Signal signalForSimulation;
    private List<Test> tests;
    
    public Project(User user, String name) {
        this.user = user;
        this.name = name;
        this.signalForSimulation = new Signal();
        this.tests = new ArrayList();
    }

    public Component getComponentModule() {
        return componentModule;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public void setComponentModule(Component componentModule) {
        this.componentModule = componentModule;
    }
    
    public List<Component> getInputs(){
        return ((ComponentModule)componentModule).getInputList();
    }
    public List<Component> getOutputs(){
        return ((ComponentModule)componentModule).getOutputList();
    }

    public Signal getSignalForSimulation() {
        return signalForSimulation;
    }

    public void setSignalForSimulation(Signal signalForSimulation) {
        this.signalForSimulation = signalForSimulation;
    }
    
    public List<Combination> runSimulation(){
        return signalForSimulation.runSimulation((ComponentModule) componentModule);
    }

    public List<Test> getTests() {
        return tests;
    }
    
    public void addTest(Test test){
        tests.add(test);
    }
    
    public boolean runTest(Test test){

        if(!tests.contains(test))
            throw new IllegalStateException("Does not exist the test: " + test + " associated with this project.");
        
        if(test.getSignalInput().getCombinations().size() != test.getSignalExpected().getCombinations().size())
            throw new IllegalStateException("The number of input combinations don't match with the number of expected results.");

        if(test.getSignalExpected().getCombinations().isEmpty() && test.getSignalInput().getCombinations().isEmpty())
            throw new IllegalStateException("It's necessary define at least one input and expected combination.");

        return test.getResult(componentModule);
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    @Override
    public String toString() {
        return "Project{" + "componentModule=" + componentModule + ", user=" + user + '}';
    }
    
}
