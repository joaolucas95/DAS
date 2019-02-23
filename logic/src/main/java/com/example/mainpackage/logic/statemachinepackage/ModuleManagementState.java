package com.example.mainpackage.logic.statemachinepackage;


import com.example.mainpackage.logic.project.CommandAddComponent;
import com.example.mainpackage.logic.project.CommandManager;
import com.example.mainpackage.logic.project.component.ComponentType;

public class ModuleManagementState extends StateAdapter{
    
    public ModuleManagementState(CommandManager commandManager) {
        super(commandManager);
    }

    @Override
    public IState addSimpleComponent(ComponentType type, int[] position) {
        CommandAddComponent cmAddComponent = new CommandAddComponent(type, position);
        commandManager.apply(cmAddComponent);
        return this;
    }
    
    @Override
    public IState selectComponent(String componentName) {
        return new DefiningPreviousState(commandManager, componentName, this);
    }
    
}
