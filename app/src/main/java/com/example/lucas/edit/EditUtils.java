package com.example.lucas.edit;

import com.example.mainpackage.logic.project.component.Component;

import java.util.Arrays;

public class EditUtils {

    static final int COMPONENT_RADIUS = 50;
    static final float LINE_THICKNESS = 10f;

    public static final String IS_SIMPLE_EXTRA = "IS_SIMPLE_EXTRA";

    static boolean isSameComponent(Component comp1, Component comp2) {
        //noinspection SimplifiableIfStatement
        if (comp1 == null || comp2 == null) {
            return false;
        }

        return Arrays.equals(comp1.getPosition(), comp2.getPosition());
    }

}