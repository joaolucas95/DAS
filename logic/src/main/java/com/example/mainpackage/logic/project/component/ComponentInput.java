package com.example.mainpackage.logic.project.component;

public class ComponentInput extends ComponentSimple {
    
    private Boolean value;
    
    public ComponentInput(String name) {
        super(name);
        this.value = null;
    }

    @Override
    public String getLogicGates() {
        return ""; //wouldn't be used
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