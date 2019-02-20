package com.example.mainpackage.logic.statemachinepackage;

import com.example.mainpackage.logic.project.CommandAddComponent;
import com.example.mainpackage.logic.project.CommandManager;
import com.example.mainpackage.logic.project.component.ComponentType;
import com.example.mainpackage.logic.user.User;

public class GlobalModuleManagementState extends StateAdapter{

    public GlobalModuleManagementState(CommandManager commandManager) {
        super(commandManager);
    }
    
    @Override
    public IState addSimpleComponent(ComponentType type) {
        
        //if type isnt a input or a output do not had new simple component
        if(!(type.equals(ComponentType.INPUT) || type.equals(ComponentType.OUTPUT)))
            return this;
        
        CommandAddComponent cmAddComponent = new CommandAddComponent(type);
        commandManager.apply(cmAddComponent);
        return this;
    }

    @Override
    public IState addModule(String projectName, User user) {
        CommandAddComponent cmAddComponent = new CommandAddComponent(ComponentType.PROJECT, projectName, user);
        commandManager.apply(cmAddComponent);
        return this;
    }
    
    

    @Override
    public IState selectComponent(String componentName) {
        return new DefiningPreviousState(commandManager, componentName, this);
    }
    

}
