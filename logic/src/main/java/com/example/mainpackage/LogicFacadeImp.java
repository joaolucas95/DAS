package com.example.mainpackage;

import com.example.mainpackage.logic.project.FileManagement.FileType;
import com.example.mainpackage.logic.project.FileManagement.FileUtils;
import com.example.mainpackage.logic.project.component.ComponentType;
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
    public List<ComponentType> getComponentsTypes() {
        return ComponentUtils.getComponentsTypes();
    }

    @Override
    public String getComponentTypeName(ComponentType type) {
        return ComponentUtils.getComponentName(type);
    }

    @Override
    public List<FileType> getFileTypes() {
        return FileUtils.getFileTypes();
    }

    @Override
    public String getFileTypeName(FileType type) {
        return FileUtils.getFileTypeName(type);
    }


}