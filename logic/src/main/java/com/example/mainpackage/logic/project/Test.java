/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.mainpackage.logic.project;

/**
 *
 * @author BrunoCoelho
 */
public class Test {
    
    private Signal signalInput;
    private Signal signalExpected;

    public Test(Signal signalInput, Signal signalExpected) {
        this.signalInput = signalInput;
        this.signalExpected = signalExpected;
    }

    public Signal getSignalInput() {
        return signalInput;
    }

    public void setSignalInput(Signal signalInput) {
        this.signalInput = signalInput;
    }

    public Signal getSignalExpected() {
        return signalExpected;
    }

    public void setSignalExpected(Signal signalExpected) {
        this.signalExpected = signalExpected;
    }
    
    public boolean getResult()
    {
        return true;
    }
}
