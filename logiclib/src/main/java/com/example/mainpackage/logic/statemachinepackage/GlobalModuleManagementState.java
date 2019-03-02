package com.example.mainpackage.logic.statemachinepackage;

import com.example.mainpackage.logic.project.CommandAddComponent;
import com.example.mainpackage.logic.project.CommandManager;
import com.example.mainpackage.logic.project.component.ComponentType;
import com.example.mainpackage.logic.user.User;

public class GlobalModuleManagementState extends StateAdapter {

    GlobalModuleManagementState(CommandManager commandManager) {
        super(commandManager);
    }

    @Override
    public IState addSimpleComponent(ComponentType type, int[] position) {
        //if type isn't a input or a output do not had new simple component
        if (!(type.equals(ComponentType.INPUT) || type.equals(ComponentType.OUTPUT)))
            return this;

        CommandAddComponent cmAddComponent = new CommandAddComponent(type, position);
        commandManager.apply(cmAddComponent);
        return this;
    }

    @Override
    public IState addModule(String filePathProject, User user, int[] position) {
        CommandAddComponent cmAddComponent = new CommandAddComponent(ComponentType.PROJECT, filePathProject, user, position);
        commandManager.apply(cmAddComponent);
        return this;
    }

    @Override
    public IState selectComponent(String componentName) {
        return new DefiningPreviousState(commandManager, componentName, this);
    }

}