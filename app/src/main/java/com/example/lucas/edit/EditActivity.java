package com.example.lucas.edit;

import com.example.lucas.logic.LogicController;
import com.example.lucas.main.R;
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

    private ComponentType mSelectedType;
    private List<ComponentType> mTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        mTypes = LogicController.getInstance().getFacade().getComponentsTypes();
        mSelectedType = mTypes.get(0);
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
                showAddDialog();
                break;

            case R.id.action_undo:
                //TODO
                Toast.makeText(this, "undo", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_redo:
                //TODO
                Toast.makeText(this, "redo", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_save:
                //TODO
                Toast.makeText(this, "save", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAddDialog() {
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

    public ComponentType getSelectedType() {
        return mSelectedType;
    }
}