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
public interface Command{
    void doCommand(ComponentBuilder componentBuilder);
    void undoCommand(ComponentBuilder componentBuilder);
}
