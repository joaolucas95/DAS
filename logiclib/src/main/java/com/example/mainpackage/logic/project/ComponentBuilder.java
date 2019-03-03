package com.example.mainpackage.logic.project;

import com.example.mainpackage.logic.project.component.Component;
import com.example.mainpackage.logic.project.component.ComponentModule;
import com.example.mainpackage.logic.project.component.ComponentType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComponentBuilder {

    private List<Component> data;

    public ComponentBuilder() {
        this.data = new ArrayList<>();
    }

    public void addNewComponent(Component component) {
        this.data.add(component);
    }


    public void connectToComponent(Command command) {

    }

    public Component build() {
        Component finalComponent = Component.getComponent(ComponentType.PROJECT, true, new int[]{0, 0});
        ((ComponentModule) finalComponent).addComponent(data);

        return finalComponent;
    }

    public void addComponentToData(Component component) {
        this.data.add(component);
    }

    public void removeComponentFromData(String componentToRemove) {
        //remove the component...
        List<Component> tmpData = new ArrayList<>(data);
        Collections.reverse(tmpData);
        for (Component component : tmpData) {
            if (component.getName().equals(componentToRemove)) {
                data.remove(component);
                break;
            }
        }
    }

    public Component findComponentWithName(String name) {
        for (Component component : data) {
            //if is a module component verify his data
            if (component instanceof ComponentModule) {
                for (Component componentOfModule : ((ComponentModule) component).getData()) {
                    if (componentOfModule.getName().equals(name))
                        return componentOfModule;
                }
            } else {
                if (component.getName().equals(name))
                    return component;
            }
        }
        return null;
    }

    public List<Component> getData() {
        return data;
    }
}
