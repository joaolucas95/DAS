package com.example.mainpackage.logic.project.component;

public class ComponentInput extends ComponentSimple {
    
    private Boolean value;
    
    ComponentInput(String name, int[] position) {
        super(name, position);
        this.value = null;
    }

    @Override
    public String getLogicGates() {
        return ""; //wouldn't be used
    }

    public ComponentInput(String name, ComponentSimple previous, int[] position) {
        super(name, position);
        this.value = null;
    }
    
    public ComponentInput(String name, Boolean value, int[] position) {
        super(name, position);
        this.value = value;
    }
    
    @Override
    public boolean getOutput(String output) {
        if (value == null) {
            return previous.get(0).getOutput(output);
        }     
        return value;
    }

    @Override
    public ComponentType getType() {
        return ComponentType.INPUT;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }
    
}