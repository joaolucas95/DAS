package com.example.lucas.edit;

import com.example.lucas.logic.LogicController;
import com.example.lucas.main.R;
import com.example.mainpackage.logic.project.Command;
import com.example.mainpackage.logic.project.CommandAddComponent;
import com.example.mainpackage.logic.project.CommandManager;
import com.example.mainpackage.logic.project.ComponentBuilder;
import com.example.mainpackage.logic.project.component.Component;
import com.example.mainpackage.logic.project.component.ComponentModule;
import com.example.mainpackage.logic.project.component.ComponentType;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EditActivity extends AppCompatActivity {

    private EditView mEditView;

    private ComponentType mSelectedType;
    private List<ComponentType> mTypes;

    private ComponentBuilder mBuilder = new ComponentBuilder();
    private CommandManager mCmdManager = new CommandManager(mBuilder);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        mTypes = LogicController.getInstance().getFacade().getComponentsTypes();
        mSelectedType = mTypes.get(0);
        mEditView = findViewById(R.id.edit_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                handleActionAdd();
                break;

            case R.id.action_undo:
                handleActionUndo();
                break;

            case R.id.action_redo:
                handleActionRedo();
                break;

            case R.id.action_save:
                handleActionSave();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void handleActionAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.choose_component);

        List<String> componentNames = new ArrayList<>();
        for (ComponentType type : mTypes) {
            componentNames.add(LogicController.getInstance().getFacade().getComponentsTypeName(type));
        }

        if (componentNames.isEmpty()) {
            Toast.makeText(this, R.string.dialog_not_found, Toast.LENGTH_SHORT).show();
            return;
        }

        String[] components = new String[componentNames.size()];
        components = componentNames.toArray(components);

        int checkedItem = 0;
        builder.setSingleChoiceItems(components, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing.
            }
        });

        builder.setPositiveButton(R.string.edit_add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int pos = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                mSelectedType = mTypes.get(pos);
            }
        });

        builder.setNegativeButton(R.string.dialog_cancel, null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void handleActionUndo() {
        mCmdManager.undo();
        doDraw();
    }

    private void handleActionRedo() {
        mCmdManager.redo();
        doDraw();
    }

    private void handleActionSave() {
        // TODO
    }

    private ComponentType getSelectedType() {
        return mSelectedType;
    }

    /* Draw handling */

    void handleTap(int[] tapPos) {
        if (intersects(tapPos)) {
            Toast.makeText(this, "intersects", Toast.LENGTH_SHORT).show();
            return;
        }

        Command cmd = new CommandAddComponent(getSelectedType(), tapPos);
        mCmdManager.apply(cmd);

        doDraw();
    }

    private void doDraw() {
        Component module = mCmdManager.finishComponentEditor();
        mEditView.drawProject(module);
    }

    private boolean intersects(int[] tapPos) {
        Component component = mCmdManager.finishComponentEditor();
        ComponentModule module = (ComponentModule) component;

        for (Component cmp : module.getData()) {
            if (intersects(tapPos, cmp)) {
                return true;
            }
        }

        return false;
    }

    private boolean intersects(int[] tapPos, Component component) {
        int[] pos = component.getPosition();
        int left = pos[0] - EditValues.COMPONENT_RADIUS;
        int top = pos[1] - EditValues.COMPONENT_RADIUS;
        int right = pos[0] + EditValues.COMPONENT_RADIUS;
        int bottom = pos[1] + EditValues.COMPONENT_RADIUS;

        return tapPos[0] >= left && tapPos[0] <= right &&
                tapPos[1] >= top && tapPos[1] <= bottom;

    }
}