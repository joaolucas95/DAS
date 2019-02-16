/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.mainpackage.logic.project.component;

import java.util.List;

public class ComponentLogicOr extends ComponentLogic {
    
    ComponentLogicOr(String name, List<Component> previous) {
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
