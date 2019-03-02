package com.example.mainpackage.logic.statemachinepackage;

import com.example.mainpackage.logic.project.CommandConnectComponent;
import com.example.mainpackage.logic.project.CommandManager;

public class DefiningPreviousState extends StateAdapter {

    private String selectedComponentName;
    private IState previousState;

    DefiningPreviousState(CommandManager commandManager, String componentName, IState previousState) {
        super(commandManager);
        this.selectedComponentName = componentName;
        this.previousState = previousState;
    }

    @Override
    public IState selectComponent(String componentName) {
        //if names are different.. define connection
        if (!selectedComponentName.equals(componentName)) {
            CommandConnectComponent cmConnectComponent = new CommandConnectComponent(selectedComponentName, componentName);
            commandManager.apply(cmConnectComponent);
        }

        selectedComponentName = null;

        if (previousState instanceof GlobalModuleManagementState)
            return new GlobalModuleManagementState(commandManager);
        else
            return new ModuleManagementState(commandManager);

    }

    @Override
    public IState cancelDefiningPrevious() {
        selectedComponentName = null;

        if (previousState instanceof GlobalModuleManagementState)
            return new GlobalModuleManagementState(commandManager);
        else
            return new ModuleManagementState(commandManager);
    }

}
