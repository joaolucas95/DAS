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
public class SystemStateMachine {
    private IState state;

    public SystemStateMachine() {
        this.state = new WaitingOperationState();
    }
    
    
}
