package com.example.lucas.edit;

import com.example.lucas.logic.LogicController;
import com.example.mainpackage.logic.project.Command;
import com.example.mainpackage.logic.project.CommandAddComponent;
import com.example.mainpackage.logic.project.CommandConnectComponent;
import com.example.mainpackage.logic.project.CommandManager;
import com.example.mainpackage.logic.project.ComponentBuilder;
import com.example.mainpackage.logic.project.component.Component;
import com.example.mainpackage.logic.project.component.ComponentModule;
import com.example.mainpackage.logic.project.component.ComponentType;

import java.util.List;

public class EditController {

    private List<ComponentType> mTypes;

    private ComponentType mSelectedType;
    private Component mSelectedComponent;

    private ComponentBuilder mBuilder;
    private CommandManager mCmdManager;

    EditController() {
        mBuilder = new ComponentBuilder();
        mCmdManager = new CommandManager(mBuilder);

        mTypes = LogicController.getInstance().getFacade().getComponentsTypes();
        mSelectedType = mTypes.get(0);
    }

    List<ComponentType> getTypes() {
        return mTypes;
    }

    String getComponentTypeName(ComponentType type) {
        return LogicController.getInstance().getFacade().getComponentsTypeName(type);
    }

    void setSelectedType(ComponentType selectedType) {
        mSelectedType = selectedType;
    }

    Component getSelectedComponent() {
        return mSelectedComponent;
    }

    Component getProject() {
        return mCmdManager.finishComponentEditor();
    }

    /* Connection logic*/

    boolean handleConnection(int[] tapPos) {
        Component component = intersects(tapPos);
        if (component == null) {
            return handleNoIntersection();
        }

        return handleIntersection(component);
    }

    private boolean handleNoIntersection() {
        if (mSelectedComponent == null) {
            return false;
        }

        mSelectedComponent = null;
        return true;
    }

    private boolean handleIntersection(Component component) {
        Component selected = mSelectedComponent;
        if (selected == null) {
            mSelectedComponent = component;
            return true;
        }

        if (!EditUtils.isSameComponent(component, selected)) {
            String previous = selected.getName();
            String next = component.getName();

            doConnection(previous, next);
        }

        mSelectedComponent = null;
        return true;
    }

    /* Actions */

    void doAdd(int[] pos) {
        Command cmd = new CommandAddComponent(mSelectedType, pos);
        mCmdManager.apply(cmd);
    }

    void doUndo() {
        mCmdManager.undo();
    }

    void doRedo() {
        mCmdManager.redo();
    }

    private void doConnection(String previous, String next) {
        Command cmd = new CommandConnectComponent(previous, next);
        mCmdManager.apply(cmd);
    }

    /* Intersection logic */

    private Component intersects(int[] tapPos) {
        Component component = getProject();
        ComponentModule module = (ComponentModule) component;

        for (Component cmp : module.getData()) {
            if (intersects(tapPos, cmp)) {
                return cmp;
            }
        }

        return null;
    }

    private boolean intersects(int[] tapPos, Component component) {
        int[] pos = component.getPosition();
        int left = pos[0] - EditUtils.COMPONENT_RADIUS;
        int top = pos[1] - EditUtils.COMPONENT_RADIUS;
        int right = pos[0] + EditUtils.COMPONENT_RADIUS;
        int bottom = pos[1] + EditUtils.COMPONENT_RADIUS;

        return tapPos[0] >= left && tapPos[0] <= right &&
                tapPos[1] >= top && tapPos[1] <= bottom;

    }
}