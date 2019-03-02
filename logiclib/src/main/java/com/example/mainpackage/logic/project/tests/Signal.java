package com.example.mainpackage.logic.project.tests;

import com.example.mainpackage.logic.project.component.Component;
import com.example.mainpackage.logic.project.component.ComponentModule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Signal implements Serializable {
    private List<Combination> combinations;

    public Signal() {
        this.combinations = new ArrayList<>();
    }

    public Signal(List<Combination> combinations) {
        this.combinations = combinations;
    }
    
    public List<Combination> getCombinations() {
        return combinations;
    }

    public void setCombinations(List<Combination> combinations) {
        this.combinations = combinations;
    }

    public List<Combination> runSimulation(ComponentModule module) {
        List<Combination> result = new ArrayList();
        Map<String, Boolean> testtmp;
        
        for(Combination combination : combinations){
            //define inputs to module
            Iterator it = combination.getValues().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                module.setInput((String)pair.getKey(), (boolean)pair.getValue());
            }
            //get result
            testtmp = new LinkedHashMap<>();
            for(Component output : module.getOutputList())
                testtmp.put(output.getName(), module.getOutput(output.getName()));
            
            result.add(new Combination(testtmp));      
        }
        return result;
    }

    @Override
    public String toString() {
        return "Signal{" +
                "combinations=" + combinations +
                '}';
    }
}
