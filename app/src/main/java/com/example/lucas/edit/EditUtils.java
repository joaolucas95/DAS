package com.example.lucas.edit;

import com.example.mainpackage.logic.project.component.Component;
import com.example.mainpackage.logic.project.component.ComponentModule;
import com.example.mainpackage.logic.project.component.ComponentType;

import java.util.Arrays;

public class EditUtils {

    static final int COMPONENT_RADIUS = 50;
    public static final float LINE_THICKNESS = 10f;

    public static final int REQUEST_CODE_CHOOSE_MODULE = 1;
    public static final String EXTRA_MODULE = "EXTRA_MODULE";

    public static final String IS_SIMPLE_EXTRA = "IS_SIMPLE_EXTRA";

    static boolean isSameComponent(Component comp1, Component comp2) {
        //noinspection SimplifiableIfStatement
        if (comp1 == null || comp2 == null) {
            return false;
        }

        if (Arrays.equals(comp1.getPosition(), comp2.getPosition())) {
            return true;
        }

        if (!(comp1 instanceof ComponentModule)) {
            return false;
        }

        ComponentModule module = (ComponentModule) comp1;
        for (Component cmp : module.getData()) {
            if (cmp.getName().equals(comp2.getName())) {
                return true;
            }
        }

        return false;
    }

    static String getInputOutputName(Component component, int index) {
        if (component.getType() == ComponentType.INPUT) {
            return "Input " + index;
        }

        return "Output " + index;
    }
}