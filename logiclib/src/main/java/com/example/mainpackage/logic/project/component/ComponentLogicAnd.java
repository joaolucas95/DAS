package com.example.mainpackage.logic.project.component;

import java.util.Arrays;
import java.util.List;

public class ComponentLogicAnd extends ComponentLogic {

    ComponentLogicAnd(String name, List<Component> previous, int[] position) {
        super(name, previous, position);
    }

    @Override
    public boolean getOutput(String output) {
        boolean result = true;
        for (Component component : previous) {
            result &= component.getOutput(output);
        }

        return result;
    }

    @Override
    public String getLogicGates() {
        char[] chars = new char[previous.size()];
        Arrays.fill(chars, '1');

        return new String(chars) + " 1 \n";
    }

    @Override
    public ComponentType getType() {
        return ComponentType.LOGIC_AND;
    }
}
