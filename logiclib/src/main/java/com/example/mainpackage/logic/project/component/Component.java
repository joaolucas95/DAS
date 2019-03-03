package com.example.mainpackage.logic.project.component;

import com.example.mainpackage.logic.project.filemanagement.File;

import java.io.Serializable;
import java.util.List;

public abstract class Component implements Serializable {

    private String name;
    private int[] position;

    public Component(String name, int[] position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public abstract boolean getOutput(String output);

    public abstract ComponentType getType();

    // TODO change this to package-private
    public abstract void setPrevious(Component... previous);

    public abstract void removePrevious(Component previous);

    public abstract List<Component> getPrevious();


    // TODO change this to package-private
    public abstract void setInput(String name, boolean value);

    public static Component getComponent(ComponentType type, boolean defineUniqueNumber, int[] position) {

        String name = defineComponentName(type);

        //DEFINE UNIQUE NUMBER FOR COMPONENT!!
        if (defineUniqueNumber)
            name = defineComponentUniqueNumber(name);

        switch (type) {
            case PROJECT:
                return new ComponentModule(name, true, position);

            case MODULE:
                return new ComponentModule(name, false, position);

            case INPUT:
                return new ComponentInput(name, position);

            case OUTPUT:
                return new ComponentOutput(name, null, position);

            case LOGIC_AND:
                return new ComponentLogicAnd(name, null, position);

            case LOGIC_OR:
                return new ComponentLogicOr(name, null, position);

            default:
                throw new IllegalStateException("invalid type:" + type);
        }
    }

    public static String defineComponentUniqueNumber(String name) {
        int lastComponentNumber = File.loadLasComponentNumber();
        lastComponentNumber++;
        File.saveLastComponentNumber(lastComponentNumber);
        return (name + "" + lastComponentNumber);
    }

    private static String defineComponentName(ComponentType type) {

        switch (type) {
            case INPUT:
                return "input";
            case LOGIC_OR:
                return "or";
            case LOGIC_AND:
                return "and";
            case MODULE:
                return "module";
            case OUTPUT:
                return "output";
            case PROJECT:
                return "project";
            default:
                throw new IllegalStateException("invalid type:" + type);
        }
    }

    @Override
    public String toString() {
        return "Component{" + "name=" + name + '}';
    }

}
