/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.mainpackage.logic.project.component;

public class ComponentInput extends ComponentSimple {
    
    private Boolean value;
    
    ComponentInput(String name) {
        super(name);
        this.value = null;
    }
    
    public ComponentInput(String name, ComponentSimple previous) {
        super(name);
        this.value = null;
    }
    
    public ComponentInput(String name, Boolean value) {
        super(name);
        this.value = value;
    }
    
    @Override
    public boolean getOutput(String output) {
        if (value == null) {
            return previous.get(0).getOutput(output);
        }     
        return value;
    }
    
    public void setValue(Boolean value) {
        this.value = value;
    }
    
}