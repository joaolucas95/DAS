package com.example.mainpackage;

import com.example.mainpackage.logic.user.User;

public class LogicFacadeImp implements ILogicFacade {

    @Override
    public void setCurrentUsername(String username) {
        User.getInstance().setUsername(username);
    }

    @Override
    public String getCurrentUsername() {
        return User.getInstance().getUsername();
    }
}