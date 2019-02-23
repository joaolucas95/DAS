package com.example.lucas.edit;

import com.example.lucas.main.R;
import com.example.mainpackage.logic.project.FileManagement.FileType;
import com.example.mainpackage.logic.project.component.Component;
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

    private EditController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        mEditView = findViewById(R.id.edit_view);
        mController = new EditController();
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
        for (ComponentType type : mController.getComponentTypes()) {
            componentNames.add(mController.getComponentTypeName(type));
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
                mController.setSelectedType(mController.getComponentTypes().get(pos));
            }
        });

        builder.setNegativeButton(R.string.dialog_cancel, null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void handleActionUndo() {
        mController.doUndo();
        doDraw();
    }

    private void handleActionRedo() {
        mController.doRedo();
        doDraw();
    }

    private void handleActionSave() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.choose_file);

        List<String> fileTypesNames = new ArrayList<>();
        for (FileType type : mController.getFileTypes()) {
            fileTypesNames.add(mController.getFileTypeName(type));
        }

        if (fileTypesNames.isEmpty()) {
            Toast.makeText(this, R.string.dialog_not_found, Toast.LENGTH_SHORT).show();
            return;
        }

        String[] fileTypes = new String[fileTypesNames.size()];
        fileTypes = fileTypesNames.toArray(fileTypes);

        int checkedItem = 0;
        builder.setSingleChoiceItems(fileTypes, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing.
            }
        });

        builder.setPositiveButton(R.string.edit_save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int pos = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                mController.doSave(mController.getFileTypes().get(pos));
            }
        });

        builder.setNegativeButton(R.string.dialog_cancel, null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /* Draw handling */

    void handleTap(int[] tapPos) {
        if (!mController.handleConnection(tapPos)) {
            mController.doAdd(tapPos);
        }

        doDraw();
    }

    private void doDraw() {
        Component module = mController.getProject();
        mEditView.drawProject(module, mController.getSelectedComponent());
    }
}