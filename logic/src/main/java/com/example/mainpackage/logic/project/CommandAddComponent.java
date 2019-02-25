package com.example.mainpackage.logic.project;

import com.example.mainpackage.logic.project.FileManagement.ProjectFileManagement;
import com.example.mainpackage.logic.project.component.Component;
import com.example.mainpackage.logic.project.component.ComponentInput;
import com.example.mainpackage.logic.project.component.ComponentModule;
import com.example.mainpackage.logic.project.component.ComponentType;
import com.example.mainpackage.logic.user.User;

import java.io.Serializable;

public class CommandAddComponent implements Command, Serializable{
    
    private ComponentType type;
    private String componentName; //after component is created this var is used (eg: need component name when doing undo)
    private String filePathProject; //used if add a module from another project
    private User user; //used if add a module from another project
    private int[] position;

    public CommandAddComponent(ComponentType type, int[] position) {
        this.type = type;
        this.filePathProject = "";
        this.position = position;
    }
    
    public CommandAddComponent(ComponentType type, String filePathProject, User user, int[] position) {
        this.type = type;
        this.filePathProject = filePathProject;
        this.user = user;
        this.position = position;
    }
    
    @Override
    public void doCommand(ComponentBuilder componentBuilder) {

        //if was added a module...
        if(!filePathProject.isEmpty()){
            String newName;
            ProjectFileManagement projectFileManagement = new ProjectFileManagement();
            ComponentModule module = null;
            try {
                module = (ComponentModule) projectFileManagement.loadProject(filePathProject, user).getComponentModule();
                module.setPosition(position);

            } catch (Exception e) {
                e.printStackTrace();
            }

            //copy component and define new names
            for(Component component : module.getData())
            {

                newName = component.getName().replaceAll("[0-9]", "");

                newName = Component.defineComponentUniqueNumber(newName); 
                component.setName(newName);
                
                //if is an input we need to remove value
                if(component instanceof ComponentInput)
                    ((ComponentInput)component).setValue(null);
                
            }


            //define new name of the module
            newName = module.getName().replaceAll("[0-9]", "");
            newName = Component.defineComponentUniqueNumber(newName);
            module.setName(newName);


            componentBuilder.addComponentToData(module);
            filePathProject="";
            user = null;
        }
        else
        {
            Component componentTmp = Component.getComponent(type, true, position);
            this.componentName = componentTmp.getName();
            componentBuilder.addComponentToData(componentTmp);
        }
    }

    @Override
    public void undoCommand(ComponentBuilder componentBuilder) {
        componentBuilder.removeComponentFromData(componentName);
    }
    
}
