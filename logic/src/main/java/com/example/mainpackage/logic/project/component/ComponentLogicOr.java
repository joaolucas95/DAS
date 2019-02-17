package com.example.mainpackage.logic.project.component;

import java.util.List;

public class ComponentLogicOr extends ComponentLogic {
    
    public ComponentLogicOr(String name, List<Component> previous) {
        super(name, previous);
    }
    
    @Override
    public boolean getOutput(String output) {
        boolean result = false;
        for (Component component : previous) {
            result |= component.getOutput(output);
        }
        
        return result;
    }
    
}
