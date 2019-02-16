/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.mainpackage.logic.project;

import java.util.Map;

public class Combination {
    
    private final Map<String, Boolean> values;
    
    public Combination(Map<String, Boolean> values) {
        this.values = values;
    }

    public Map<String, Boolean> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return "Combination{" + "values=" + values + '}';
    }
    
    
}
