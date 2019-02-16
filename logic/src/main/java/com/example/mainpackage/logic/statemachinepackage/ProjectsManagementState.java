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
public class ProjectsManagementState implements IState{
    @Override
    public IState getProjectManagement() {
        return this;
    }

    @Override
    public IState getGlobalModuleManagement() {
        return this;
    }

    @Override
    public IState getModuleManagement() {
        return this;
    }

    @Override
    public IState finishProjectManagement() {
        return this;
    }

    @Override
    public IState addModule() {
        return this;
    }

    @Override
    public IState selectModule() {
        return this;
    }

    @Override
    public IState finishGlobalModuleManagement() {
        return this;
    }

    @Override
    public IState cancelDefiningPrevious() {
        return this;
    }

    @Override
    public IState definePrevious() {
        return this;
    }

    @Override
    public IState addSimpleComponent() {
        return this;
    }

    @Override
    public IState selectSimpleComponent() {
        return this;
    }

    @Override
    public IState finishModuleManagement() {
        return this;
    }
}
