/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.mainpackage.logic.project.component;

import java.util.List;

public class ComponentOutput extends ComponentSimple {

    ComponentOutput(String name, List<Component> previous) {
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
