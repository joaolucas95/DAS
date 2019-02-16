/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.mainpackage.logic.project.component;

import java.util.List;

abstract class ComponentLogic extends ComponentSimple {
    
    ComponentLogic(String name, List<Component> previous) {
        super(name);
        this.previous = previous;
    }
}