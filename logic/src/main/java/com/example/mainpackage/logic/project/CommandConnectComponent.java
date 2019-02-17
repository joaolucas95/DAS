package com.example.mainpackage.logic.project;

import com.example.mainpackage.logic.project.component.Component;

import java.io.Serializable;

public class CommandConnectComponent implements Command, Serializable{

    String componentName1, componentName2;
    
    public CommandConnectComponent(String componentName1, String componentName2) {
        this.componentName1 = componentName1;
        this.componentName2 = componentName2;
    }
   
    @Override
    public void doCommand(ComponentBuilder componentBuilder) {
        
        Component c1 = componentBuilder.findComponentWithName(componentName1);
        Component c2 = componentBuilder.findComponentWithName(componentName2);

        if(c1 == null || c2 == null)
            throw new IllegalStateException("Problems finding components with that name.");

        c2.setPrevious(c1);
    }

    @Override
    public void undoCommand(ComponentBuilder componentBuilder) {
        Component c1 = componentBuilder.findComponentWithName(componentName1);
        Component c2 = componentBuilder.findComponentWithName(componentName2);

        if(c1 == null || c2 == null)
            throw new IllegalStateException("Problems finding components with that name.");

        c2.removePrevious(c1);
    }
}
