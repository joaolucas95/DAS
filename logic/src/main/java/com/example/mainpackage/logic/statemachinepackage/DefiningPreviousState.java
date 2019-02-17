package com.example.mainpackage.logic.statemachinepackage;

import com.example.mainpackage.logic.project.CommandConnectComponent;
import com.example.mainpackage.logic.project.CommandManager;

public class DefiningPreviousState extends StateAdapter
{

    String componentName1, componentName2;
    IState previousState;
    
    public DefiningPreviousState(CommandManager commandManager, String componentName1, IState previousState) {
        super(commandManager);
        this.componentName1 = componentName1;
        this.previousState = previousState;
    } 

    @Override
    public IState selectComponent(String componentName) {
        this.componentName2 = componentName;
        //if names are different.. define connection
        if(!componentName1.equals(componentName2))
        {
            CommandConnectComponent cmConnectComponent = new CommandConnectComponent(componentName1, componentName2);
            commandManager.apply(cmConnectComponent);
        }
        
        componentName1 = null;
        componentName2 = null;
        
        if(previousState instanceof GlobalModuleManagementState)
            return new GlobalModuleManagementState(commandManager);
        else
            return new ModuleManagementState(commandManager);
        
    }

    @Override
    public IState cancelDefiningPrevious() {
        componentName1 = null;
        if(previousState instanceof GlobalModuleManagementState)
            return new GlobalModuleManagementState(commandManager);
        else
            return new ModuleManagementState(commandManager);
    }
    
}
