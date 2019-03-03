package com.example.mainpackage.logic.project.tests;

import java.io.Serializable;
import java.util.Map;

public class Combination implements Serializable {

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
