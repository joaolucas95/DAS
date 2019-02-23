package com.example.mainpackage.logic.project.component;

import java.util.Arrays;
import java.util.List;

public class ComponentLogicOr extends ComponentLogic {

    ComponentLogicOr(String name, List<Component> previous, int[] position) {
        super(name, previous, position);
    }
    
    @Override
    public boolean getOutput(String output) {
        boolean result = false;
        for (Component component : previous) {
            result |= component.getOutput(output);
        }
        
        return result;
    }

    @Override
    public String getLogicGates() {
        StringBuilder str = new StringBuilder();

        for(int i = 0 ; i < previous.size(); i++){
            char[] chars = new char[previous.size()];
            Arrays.fill(chars, '-');
            StringBuilder myName = new StringBuilder(new String(chars));
            myName.setCharAt(i, '1');
            str.append(myName.toString()).append(" 1 \n");
        }

        return str.toString();
    }

    @Override
    public ComponentType getType() {
        return ComponentType.LOGIC_OR;
    }
}
