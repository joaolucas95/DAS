package com.example.lucas.edit;

import com.example.lucas.logic.LogicController;
import com.example.mainpackage.logic.project.FileManagement.FileType;
import com.example.mainpackage.logic.project.Project;
import com.example.mainpackage.logic.project.component.Component;
import com.example.mainpackage.logic.project.component.ComponentModule;
import com.example.mainpackage.logic.project.component.ComponentType;

import java.util.List;

public class EditController {

    private ComponentType mSelectedType;
    private Component mSelectedComponent;

    EditController(boolean isSimpleProject) {
        LogicController.getInstance().getFacade().newEdition(isSimpleProject);

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
        return LogicController.getInstance().getFacade().getProjectInEdition();
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
        LogicController.getInstance().getFacade().cancelConnection();
        if (mSelectedComponent == null) {
            return false;
        }

        mSelectedComponent = null;
        return true;
    }

    private boolean handleIntersection(Component component) {
        LogicController.getInstance().getFacade().selectComponent(component.getName());
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
        LogicController.getInstance().getFacade().addComponent(mSelectedType, pos);
    }

    void doUndo() {
        LogicController.getInstance().getFacade().undoOperation();
    }

    void doRedo() {
        LogicController.getInstance().getFacade().redoOperation();
    }

    void doSave(String filePath, FileType fileType) {
        String projectName = "modelTest";
        Project project = new Project(com.example.mainpackage.logic.user.User.getInstance(), projectName);
        LogicController.getInstance().getFacade().saveProject(project, filePath, fileType);
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