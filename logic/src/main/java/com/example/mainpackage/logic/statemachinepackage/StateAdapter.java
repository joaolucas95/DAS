package com.example.mainpackage.logic.statemachinepackage;

import com.example.mainpackage.logic.project.CommandManager;
import com.example.mainpackage.logic.project.component.ComponentType;
import com.example.mainpackage.logic.user.User;

public class StateAdapter implements IState{

    protected CommandManager commandManager;

    public StateAdapter(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }
    
    @Override
    public IState addModule(String filePathProject, User user) {
        return this;
    }
    
    @Override
    public IState cancelDefiningPrevious() {
        return this;
    }

    @Override
    public IState addSimpleComponent(ComponentType type, int[] position) {
        return this;
    }

    @Override
    public IState selectComponent(String componentName) {
        return this;
    }
    
}
