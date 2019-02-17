package com.example.mainpackage.logic.project;

import com.example.mainpackage.logic.project.component.Component;
import com.example.mainpackage.logic.project.component.ComponentModule;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Test {
    
    private Signal signalInput;
    private Signal signalExpected;

    public Test(Signal signalInput, Signal signalExpected) {
        this.signalInput = signalInput;
        this.signalExpected = signalExpected;
    }

    public Signal getSignalInput() {
        return signalInput;
    }

    public void setSignalInput(Signal signalInput) {
        this.signalInput = signalInput;
    }

    public Signal getSignalExpected() {
        return signalExpected;
    }

    public void setSignalExpected(Signal signalExpected) {
        this.signalExpected = signalExpected;
    }
    
    public boolean getResult(Component module)
    {
        List<Combination> result = signalInput.runSimulation((ComponentModule) module);
        
        
        for(int i = 0; i< result.size(); i++)
        {
            Combination combinationInput = result.get(i);
            Combination combinationExpected = signalExpected.getCombinations().get(i);

            Iterator it = combinationInput.getValues().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                
                Boolean resultInput = combinationInput.getValues().get((String)pair.getKey());
                Boolean resultExpected = combinationExpected.getValues().get((String)pair.getKey());
                
                if(!resultInput.equals(resultExpected))
                    return false;
            }
        }
        
        return true;
    }
}
