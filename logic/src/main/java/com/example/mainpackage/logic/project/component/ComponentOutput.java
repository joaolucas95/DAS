package com.example.mainpackage.logic.project.component;

import java.util.List;

public class ComponentOutput extends ComponentSimple {

    public ComponentOutput(String name, List<Component> previous) {
        super(name);
    }

    @Override
    public boolean getOutput(String output) {
        if (previous == null) {
            throw new IllegalStateException("no previous component");
        }
        
        return previous.get(0).getOutput(output);
    }
    
}
