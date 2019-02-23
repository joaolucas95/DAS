package com.example.mainpackage.logic.project.component;

import java.util.List;

abstract class ComponentLogic extends ComponentSimple {
    
    ComponentLogic(String name, List<Component> previous, int[] position) {
        super(name, position);
        this.previous = previous;
    }
}