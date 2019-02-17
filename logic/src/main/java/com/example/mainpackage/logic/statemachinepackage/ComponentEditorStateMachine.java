package com.example.mainpackage.logic.statemachinepackage;

import com.example.mainpackage.logic.project.CommandManager;
import com.example.mainpackage.logic.project.ComponentBuilder;
import com.example.mainpackage.logic.project.component.Component;
import com.example.mainpackage.logic.project.component.ComponentType;

public class ComponentEditorStateMachine {
    private IState state;
    private CommandManager commandManager;

    public ComponentEditorStateMachine(ComponentType type) {
        this.commandManager = new CommandManager(new ComponentBuilder());
        if(type.equals(ComponentType.PROJECT))
            this.state = new GlobalModuleManagementState(commandManager);
        else if(type.equals(ComponentType.MODULE))
            this.state = new ModuleManagementState(commandManager);
        else
            throw new IllegalStateException("Only can initilize an component editor with components of type projects or modules.");
    }

    public IState getState() {
        return state;
    }

    public void setState(IState state) {
        this.state = state;
    }
    
    public void addModule(String projectName){
        setState(state.addModule(projectName));
    }
    
    public void cancelDefiningPrevious(){
        setState(state.cancelDefiningPrevious());
    }
    
    public void addSimpleComponent(ComponentType type) {
        setState(state.addSimpleComponent(type));
    }
    
    public void selectComponent(String componentName){
        setState(state.selectComponent(componentName));
    }
    
    public Component finishComponentEditor(){
        return commandManager.finishComponentEditor();
    }
    
    @Override
    public String toString() {
        return "ComponentEditorStateMachine{" + "state=" + state + '}';
    }
    
    
}