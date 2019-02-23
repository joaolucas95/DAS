package com.example.mainpackage.logic.statemachinepackage;

import com.example.mainpackage.logic.project.component.ComponentType;
import com.example.mainpackage.logic.user.User;

public interface IState {
        
    IState addModule(String filePathProject, User user); //for GlobalModuleManagement
    //for GlobalModuleManagement and ModuleManagement
    //when call this method in GlobalModuleManagement or ModuleManagement we are defining the first component
    //when we call this method in DefiningPreviousState we are defining  the second component... And define the connection between the selected components
    IState selectComponent(String componentName);
    IState cancelDefiningPrevious(); //for DefiningPreviousState
    IState addSimpleComponent(ComponentType type, int[] position); //for ModulesManagementState


    
}
