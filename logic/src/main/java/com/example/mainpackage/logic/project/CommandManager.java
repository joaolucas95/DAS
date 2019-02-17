package com.example.mainpackage.logic.project;

import com.example.mainpackage.logic.project.component.Component;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    List<Command> undoList;
    List<Command> redoList;
    
    protected ComponentBuilder componentBuilder;

    public CommandManager(ComponentBuilder componentBuilder) {
        this.componentBuilder = componentBuilder;
        this.undoList = new ArrayList();
        this.redoList = new ArrayList();
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
    
    public Component finishComponentEditor(){
        return componentBuilder.build();
    }
}
