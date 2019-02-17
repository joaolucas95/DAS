package com.example.mainpackage;

import com.example.mainpackage.logic.project.component.ComponentUtils;
import com.example.mainpackage.logic.user.User;

import java.util.List;

public class LogicFacadeImp implements ILogicFacade {

    @Override
    public void setCurrentUsername(String username) {
        User.getInstance().setUsername(username);
    }

    @Override
    public String getCurrentUsername() {
        return User.getInstance().getUsername();
    }

    @Override
    public List<String> getComponentsNames() {
        return ComponentUtils.getComponentsNames();
    }
}