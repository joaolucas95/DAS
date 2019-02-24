package com.example.lucas.edit;

import com.example.lucas.logic.LogicController;
import com.example.mainpackage.logic.project.FileManagement.FileType;
import com.example.mainpackage.logic.project.component.Component;
import com.example.mainpackage.logic.project.component.ComponentModule;
import com.example.mainpackage.logic.project.component.ComponentType;
import com.example.mainpackage.logic.statemachinepackage.ComponentEditorStateMachine;

import java.util.List;

public class EditController {

    private ComponentType mSelectedType;
    private Component mSelectedComponent;

    private ComponentEditorStateMachine mEditorStateMachine;

    EditController(boolean isSimpleProject) {
        ComponentType type = isSimpleProject ? ComponentType.MODULE : ComponentType.PROJECT;
        mEditorStateMachine = new ComponentEditorStateMachine(type);

        List<ComponentType> types = getComponentTypes();
        mSelectedType = types.get(0);
    }

    List<ComponentType> getComponentTypes() {
        return LogicController.getInstance().getFacade().getComponentsTypes();
    }

    String getComponentTypeName(ComponentType type) {
        return LogicController.getInstance().getFacade().getComponentTypeName(type);
    }

    List<FileType> getFileTypes() {
        return LogicController.getInstance().getFacade().getFileTypes();
    }

    String getFileTypeName(FileType type) {
        return LogicController.getInstance().getFacade().getFileTypeName(type);
    }

    void setSelectedType(ComponentType selectedType) {
        mSelectedType = selectedType;
    }

    Component getSelectedComponent() {
        return mSelectedComponent;
    }

    Component getProject() {
        return mEditorStateMachine.finishComponentEditor();
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
        mEditorStateMachine.cancelDefiningPrevious();
        if (mSelectedComponent == null) {
            return false;
        }

        mSelectedComponent = null;
        return true;
    }

    private boolean handleIntersection(Component component) {
        mEditorStateMachine.selectComponent(component.getName());
        Component selected = mSelectedComponent;
        if (selected == null) {
            mSelectedComponent = component;
            return true;
        }

        mSelectedComponent = null;
        return true;
    }

    /* Actions */

    void doAdd(int[] pos) {
        mEditorStateMachine.addSimpleComponent(mSelectedType, pos);
    }

    void doUndo() {
        mEditorStateMachine.undoOperation();
    }

    void doRedo() {
        mEditorStateMachine.redoOperation();
    }

    void doSave(FileType fileType) {
        // TODO save according file type
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