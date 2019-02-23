package com.example.mainpackage.logic.project.component;

import com.example.mainpackage.logic.project.FileManagement.File;

import java.io.Serializable;
import java.util.List;

public abstract class Component implements Serializable{

    private String name;
    
    public Component(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    
    public abstract boolean getOutput(String output);
    
    // TODO change this to package-private
    public abstract void setPrevious(Component... previous);
    public abstract void removePrevious(Component previous);

    public abstract List<Component> getPrevious();

        
    // TODO change this to package-private
    public abstract void setInput(String name, boolean value);
    
    public static Component getComponent(ComponentType type, boolean defineUniqueNumber) {
        
        //DEFINE UNIQUE NUMBER FOR COMPONENT!!
        String name = defineComponentName(type);

        if(defineUniqueNumber)
            name = defineComponentUniqueNumber(name);
        
        switch (type) {
            case PROJECT:
                return new ComponentModule(name, true);
                
            case MODULE:
                return new ComponentModule(name, false);
                
            case INPUT:
                return new ComponentInput(name);
                
            case OUTPUT:
                return new ComponentOutput(name, null);
                
            case LOGIC_AND:
                return new ComponentLogicAnd(name, null);
                
            case LOGIC_OR:
                return new ComponentLogicOr(name, null);
                
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
    
    public int getUniqueNumber(){
        String str = new String(name);
        String numberstr = str.replaceAll("[^\\d.]", "");
        return Integer.parseInt(numberstr);
    }
    
    private static String defineComponentName(ComponentType type) {

        switch(type){
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
