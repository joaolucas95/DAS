package com.example.lucas.logic;

import com.example.mainpackage.ILogicFacade;
import com.example.mainpackage.LogicFacadeImp;

public class LogicController {

    private static LogicController sInstance;

    private ILogicFacade mLogicFacade;

    public static LogicController getInstance() {
        if (sInstance == null) {
            synchronized (LogicController.class) {
                if (sInstance == null) {
                    sInstance = new LogicController();
                }
            }
        }

        return sInstance;
    }

    private LogicController() {
        mLogicFacade = new LogicFacadeImp();
    }

    public ILogicFacade getFacade() {
        return mLogicFacade;
    }

}