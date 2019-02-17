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
