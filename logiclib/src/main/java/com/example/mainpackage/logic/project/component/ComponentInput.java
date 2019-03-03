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