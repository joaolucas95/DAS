package com.example.mainpackage.logic.project;

import com.example.mainpackage.logic.project.FileManagement.File;
import com.example.mainpackage.logic.project.FileManagement.ProjectFileManagement;
import com.example.mainpackage.logic.project.component.Component;
import com.example.mainpackage.logic.project.component.ComponentInput;
import com.example.mainpackage.logic.project.component.ComponentModule;
import com.example.mainpackage.logic.project.component.ComponentType;
import com.example.mainpackage.logic.user.User;

import java.io.Serializable;

public class CommandAddComponent implements Command, Serializable{
    
    ComponentType type;
    String componentName; //after component is created this var is used (eg: need component name when doing undo)
    String projectName; //used if add a module from another project
    User user; //used if add a module from another project

    public CommandAddComponent(ComponentType type) {
        this.type = type;
        this.projectName = new String();
    }
    
    public CommandAddComponent(ComponentType type, String projectName, User user) {
        this.type = type;
        this.projectName = projectName;
        this.user = user;
    }
    
    @Override
    public void doCommand(ComponentBuilder componentBuilder) {

        //if was added a module...
        if(!projectName.isEmpty()){
            ProjectFileManagement projectFileManagement = new ProjectFileManagement();
            ComponentModule module = null;
            try {
                module = (ComponentModule) projectFileManagement.loadProject(projectName, user).getComponentModule();
            } catch (Exception e) {
                e.printStackTrace();
            }

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
            user = null;
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
