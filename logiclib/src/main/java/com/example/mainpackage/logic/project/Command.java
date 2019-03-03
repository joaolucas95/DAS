package com.example.mainpackage.logic.project;

public interface Command {
    void doCommand(ComponentBuilder componentBuilder);

    void undoCommand(ComponentBuilder componentBuilder);
}
