/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.mainpackage.logic.project;

import com.example.mainpackage.logic.project.FileManagement.File;
import com.example.mainpackage.logic.project.component.Component;
import com.example.mainpackage.logic.project.component.ComponentInput;
import com.example.mainpackage.logic.project.component.ComponentModule;
import com.example.mainpackage.logic.project.component.ComponentType;

import java.io.Serializable;

public class CommandAddComponent implements Command, Serializable{

    private ComponentType type;
    private String componentName; //after component is created this var is used (eg: need component name when doing undo)
    private String projectName; //used if add a module from another project

    public CommandAddComponent(ComponentType type) {
        this.type = type;
        this.projectName = "";
    }

    public CommandAddComponent(ComponentType type, String projectName) {
        this.type = type;
        this.projectName = projectName;
    }

    @Override
    public void doCommand(ComponentBuilder componentBuilder) {

        if(!projectName.isEmpty()){
            ComponentModule module = (ComponentModule) File.getModuleByName("", projectName);

            //copy component and define new names
            for(Component component : module.getData())
            {
                String newName;

                newName = component.getName().replaceAll("[0-9]", "");

                newName = Component.defineComponentUniqueNumber(newName);
                component.setName(newName);

                //if is an input we need to remove value
                if(component instanceof ComponentInput)
                    ((ComponentInput)component).setValue(null);

            }
            componentBuilder.addComponentToData(module);
            projectName="";
        }
        else
        {
            Component componentTmp = Component.getComponent(type);
            this.componentName = componentTmp.getName();
            componentBuilder.addComponentToData(componentTmp);
        }
    }

    @Override
    public void undoCommand(ComponentBuilder componentBuilder) {
        componentBuilder.removeComponentFromData(componentName);
    }

}
