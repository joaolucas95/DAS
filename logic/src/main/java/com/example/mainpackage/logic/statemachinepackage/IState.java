/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.mainpackage.logic.statemachinepackage;

/**
 *
 * @author BrunoCoelho
 */
public interface IState {
    
    IState getProjectManagement(); //for WaitingOperationState

    IState getGlobalModuleManagement(); //for ProjectsManagementState
    IState getModuleManagement(); //for ProjectsManagementState
    IState finishProjectManagement(); //for ProjectsManagementState

    
    IState addModule(); //for GlobalModuleManagement
    IState selectModule(); //for GlobalModuleManagement
    IState finishGlobalModuleManagement(); //for GlobalModuleManagement

    IState cancelDefiningPrevious(); //for DefiningPreviousState
    IState definePrevious(); //for DefiningPreviousState
    
    
    IState addSimpleComponent(); //for ModulesManagementState
    IState selectSimpleComponent(); //for ModulesManagementState
    IState finishModuleManagement(); //for ModulesManagementState


    
}
