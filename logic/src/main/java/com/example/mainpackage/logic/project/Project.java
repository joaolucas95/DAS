/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.mainpackage.logic.project;

import com.example.mainpackage.logic.project.component.Component;
import com.example.mainpackage.logic.project.component.ComponentModule;
import com.example.mainpackage.logic.user.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Project implements Serializable{

    private Component componentModule;
    private User user;
    private String name;
    private Signal signalForSimulation;
    private List<Test> tests;

    public Project(User user, String name) {
        this.user = user;
        this.name = name;
        this.tests = new ArrayList<>();
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

        List<Combination> result = new ArrayList<>();
        Map<String, Boolean> testtmp;

        for(Combination simulation : signalForSimulation.getCombinations()){
            //define inputs to module
            Iterator it = simulation.getValues().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                getComponentModule().setInput((String)pair.getKey(), (boolean)pair.getValue());
            }
            //get result
            testtmp = new HashMap<>();
            for(Component output : getOutputs())
                testtmp.put(output.getName(), getComponentModule().getOutput(output.getName()));

            result.add(new Combination(testtmp));
        }
        return result;
    }

    public List<Test> getTests() {
        return tests;
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
