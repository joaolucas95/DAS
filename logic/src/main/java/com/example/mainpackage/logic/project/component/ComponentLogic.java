package com.example.mainpackage.logic.project.component;

import java.util.List;

public abstract class ComponentLogic extends ComponentSimple {
    
    public ComponentLogic(String name, List<Component> previous) {
        super(name);
        this.previous = previous;
    }
}