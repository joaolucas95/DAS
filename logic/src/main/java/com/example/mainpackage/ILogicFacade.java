package com.example.mainpackage;

import java.util.List;

public interface ILogicFacade {

    void setCurrentUsername(String username);

    String getCurrentUsername();

    List<String> getComponentsNames();
}