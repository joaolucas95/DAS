package com.example.mainpackage.logic.project.component;

import java.util.Arrays;
import java.util.List;

public class ComponentOutput extends ComponentSimple {

    ComponentOutput(String name, List<Component> previous, int[] position) {
        super(name, position);
    }

    @Override
    public boolean getOutput(String output) {
        if (previous == null) {
            throw new IllegalStateException("no previous component");
        }

        return previous.get(0).getOutput(output);
    }

    @Override
    public String getLogicGates() {
        char[] chars = new char[previous.size()];
        Arrays.fill(chars, '1');

        return new String(chars) + " 1 \n";
    }

    @Override
    public ComponentType getType() {
        return ComponentType.OUTPUT;
    }
}
