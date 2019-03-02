package com.example.mainpackage.logic.statemachinepackage;

import com.example.mainpackage.logic.project.CommandManager;
import com.example.mainpackage.logic.project.ComponentBuilder;
import com.example.mainpackage.logic.project.component.Component;
import com.example.mainpackage.logic.project.component.ComponentType;
import com.example.mainpackage.logic.project.component.ComponentUtils;
import com.example.mainpackage.logic.user.User;

import java.util.List;

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
            throw new IllegalStateException("Only can initialize an component editor with components of type projects or modules.");
    }

    public IState getState() {
        return state;
    }

    private void setState(IState state) {
        this.state = state;
    }
    
    public void addModule(String filePathProject, User user, int[] position){
        setState(state.addModule(filePathProject, user, position));
    }
    
    public void cancelDefiningPrevious(){
        setState(state.cancelDefiningPrevious());
    }
    
    public void addSimpleComponent(ComponentType type, int[] position) {
        setState(state.addSimpleComponent(type, position));
    }
    
    public void selectComponent(String componentName){
        setState(state.selectComponent(componentName));
    }

    public void undoOperation() {
        setState(state.undoOperation());
    }

    public void redoOperation() {
        setState(state.redoOperation());
    }
    
    public Component finishComponentEditor(){
        return commandManager.finishComponentEditor();
    }

    public List<ComponentType> getComponentTypes() {
        if (state instanceof GlobalModuleManagementState) {
            return ComponentUtils.getComponentsTypes(ComponentType.PROJECT);
        }

        return ComponentUtils.getComponentsTypes(ComponentType.MODULE);
    }
    
    @Override
    public String toString() {
        return "ComponentEditorStateMachine{" + "state=" + state + '}';
    }

    public List<Component> getActualDataToDraw(){ return commandManager.getActualDataToDraw();}

}
