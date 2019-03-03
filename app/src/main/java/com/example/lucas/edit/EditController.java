package com.example.lucas.edit;

import com.example.lucas.logic.LogicController;
import com.example.lucas.main.R;
import com.example.mainpackage.logic.project.Project;
import com.example.mainpackage.logic.project.component.Component;
import com.example.mainpackage.logic.project.component.ComponentModule;
import com.example.mainpackage.logic.project.component.ComponentType;
import com.example.mainpackage.logic.project.filemanagement.FileType;
import com.example.mainpackage.logic.user.User;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

public class EditController {

    private ComponentType mSelectedType;
    private Component mSelectedComponent;
    private String mSelectedModulePath;

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

    List<Component> getActualComponents() {
        return LogicController.getInstance().getFacade().getActualComponents();
    }

    void setSelectedModule(String modulePath) {
        mSelectedModulePath = modulePath;
    }

    /* Connection logic*/

    boolean handleConnection(int[] tapPos, EditActivity context) {
        Component component = intersects(tapPos);
        if (component == null) {
            return handleNoIntersection();
        }

        return handleIntersection(component, context);
    }

    private boolean handleNoIntersection() {
        LogicController.getInstance().getFacade().cancelConnection();
        if (mSelectedComponent == null) {
            return false;
        }

        mSelectedComponent = null;
        return true;
    }

    private boolean handleIntersection(Component component, EditActivity context) {
        if (component.getType() == ComponentType.MODULE || component.getType() == ComponentType.PROJECT) {
            handleModuleIntersection((ComponentModule) component, context);
            return true;
        }

        LogicController.getInstance().getFacade().selectComponent(component.getName());
        Component selected = mSelectedComponent;
        if (selected == null) {
            mSelectedComponent = component;
            return true;
        }

        mSelectedComponent = null;
        return true;
    }

    private void handleModuleIntersection(final ComponentModule component, final EditActivity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.choose_component);

        final List<Component> components = mSelectedComponent == null ?
                component.getOutputList() : component.getInputList();

        List<String> inputOutputNames = new ArrayList<>();
        for (int i = 0; i < components.size(); i++) {
            Component cmp = components.get(i);
            inputOutputNames.add(EditUtils.getInputOutputName(cmp, i + 1));
        }

        String[] fileTypes = new String[inputOutputNames.size()];
        fileTypes = inputOutputNames.toArray(fileTypes);

        int checkedItem = 0;
        builder.setSingleChoiceItems(fileTypes, checkedItem, null);

        builder.setPositiveButton(R.string.select, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int pos = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                Component inputOutputSelected = components.get(pos);
                handleIntersection(inputOutputSelected, context);

                context.doDraw();
            }
        });

        builder.setNegativeButton(R.string.dialog_cancel, null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /* Actions */

    void doAdd(int[] pos) {
        if (mSelectedType != ComponentType.MODULE || mSelectedModulePath == null) {
            LogicController.getInstance().getFacade().addComponent(mSelectedType, pos);
            return;
        }

        User user = User.getInstance();
        LogicController.getInstance().getFacade().addModule(mSelectedModulePath, user, pos);
    }

    void doUndo() {
        LogicController.getInstance().getFacade().undoOperation();
    }

    void doRedo() {
        LogicController.getInstance().getFacade().redoOperation();
    }

    void doSave(String filePath, FileType fileType, String projectName) {
        Project project = new Project(com.example.mainpackage.logic.user.User.getInstance(), projectName);
        LogicController.getInstance().getFacade().saveProject(project, true, filePath, fileType);
    }

    /* Intersection logic */

    private Component intersects(int[] tapPos) {
        List<Component> actualComponents = getActualComponents();
        for (Component cmp : actualComponents) {
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