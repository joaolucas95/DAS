/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.mainpackage.logic.project;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BrunoCoelho
 */
public class CommandManager {
    private List<Command> undoList;
    private List<Command> redoList;
    
    private ComponentBuilder componentBuilder;

    public CommandManager(ComponentBuilder componentBuilder) {
        this.componentBuilder = componentBuilder;
        this.undoList = new ArrayList<>();
        this.redoList = new ArrayList<>();
    }

    public void apply(Command c){
        c.doCommand(componentBuilder);
        redoList.clear();
        undoList.add(c);
    }
    
    public void undo(){
        if(undoList.isEmpty())
            return;
        Command last = undoList.remove(undoList.size()-1);
        last.undoCommand(componentBuilder);
        redoList.add(last);
    }
    
    public void redo(){
        if(redoList.isEmpty())
            return;
        Command last =redoList.remove(redoList.size()-1);
        last.doCommand(componentBuilder);
        undoList.add(last);
    }
}
