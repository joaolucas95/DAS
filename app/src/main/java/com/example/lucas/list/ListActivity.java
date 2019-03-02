package com.example.lucas.list;

import com.example.lucas.edit.EditActivity;
import com.example.lucas.edit.EditUtils;
import com.example.lucas.logic.LogicController;
import com.example.lucas.main.R;
import com.example.mainpackage.logic.dblogic.FilePath;
import com.example.mainpackage.logic.dblogic.User;
import com.example.mainpackage.logic.project.component.Component;
import com.example.mainpackage.logic.project.component.ComponentType;
import com.example.mainpackage.logic.statemachinepackage.ComponentEditorStateMachine;

import android.app.AlertDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setUiComponents();
    }

    private void setUiComponents() {
        setupToolbar();
        setNewButton();
        setRecyclerView();
    }

    private void setupToolbar() {
        String username = LogicController.getInstance().getFacade().getCurrentUsername();

        String helloMessage = getString(R.string.hello_message, username);

        //noinspection ConstantConditions
        getSupportActionBar().setTitle(helloMessage);
    }

    private void setNewButton() {
        findViewById(R.id.btn_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //just for test
                //createModelTestBin();

                handleNewProject();
            }
        });
    }

    //for tests
    private void createModelTestBin() {
        /*

        String username = LogicController.getInstance().getFacade().getCurrentUsername();
        User user = mFileHistoryViewModel.findUserByUsername(username);

        String projectName = "modelTest";
        String filePathString = getApplicationContext().getFilesDir().getPath().toString() + "/" + projectName + ".blif"; //shoud be setted when save project... should be something like: getApplicationContext().getFilesDir().getPath().toString() + "/" + projectName + ".bin"

        FilePath filePath = new FilePath(projectName, filePathString, user.id);
        mFileHistoryViewModel.insertFilePath(filePath);


        try {
            Component model = createModelTestWithStateMachine();
            Project project = new Project(com.example.mainpackage.logic.user.User.getInstance(), projectName);
            project.setComponentModule(model);
            //Log.d("test", "Project created:" + project);
            LogicController.getInstance().getFacade().saveProject(project, true, getApplicationContext().getFilesDir().getPath().toString(), FileType.BLIF);

        } catch (Exception ex) {
            Log.d("test", "Project already created");
        }


        try {
            //for test - test create global module and save as blif file and load
            String projectNameTest = "modelGlobalTest";
            Component model = createGlobalModuleTestWithStateMachine(com.example.mainpackage.logic.user.User.getInstance(), getApplicationContext());

            Project project = new Project(com.example.mainpackage.logic.user.User.getInstance(), projectNameTest);
            project.setComponentModule(model);

            LogicController.getInstance().getFacade().saveProject(project, true, getApplicationContext().getFilesDir().getPath().toString(), FileType.BLIF);

            project = null;
            project = LogicController.getInstance().getFacade().getProject(getApplicationContext().getFilesDir().getPath().toString() + "/modelGlobalTest.blif");
            Log.d("test", "Project loaded:" + project);

        } catch (Exception ex) {
            Log.d("test", "Error:" + ex);
        }
 */

    }

    //for tests
    private static Component createModelTestWithStateMachine() {
        ComponentEditorStateMachine stateMachine = new ComponentEditorStateMachine(ComponentType.MODULE);
        stateMachine.addSimpleComponent(ComponentType.INPUT, new int[]{2, 3});
        stateMachine.addSimpleComponent(ComponentType.INPUT, new int[]{0, 0});
        stateMachine.addSimpleComponent(ComponentType.INPUT, new int[]{0, 0});

        stateMachine.addSimpleComponent(ComponentType.LOGIC_AND, new int[]{0, 0});

        //test
        stateMachine.selectComponent("and4");
        stateMachine.selectComponent("and4");

        stateMachine.selectComponent("input1");
        stateMachine.selectComponent("and4");
        stateMachine.selectComponent("input2");
        stateMachine.selectComponent("and4");

        stateMachine.addSimpleComponent(ComponentType.LOGIC_AND, new int[]{0, 0});
        stateMachine.selectComponent("input2");
        stateMachine.selectComponent("and5");
        stateMachine.selectComponent("input3");
        stateMachine.selectComponent("and5");

        stateMachine.addSimpleComponent(ComponentType.LOGIC_OR, new int[]{0, 0});
        stateMachine.selectComponent("and4");
        stateMachine.selectComponent("or6");
        stateMachine.selectComponent("and5");
        stateMachine.selectComponent("or6");

        stateMachine.addSimpleComponent(ComponentType.OUTPUT, new int[]{0, 0});
        stateMachine.selectComponent("and4");
        stateMachine.selectComponent("output7");

        stateMachine.addSimpleComponent(ComponentType.OUTPUT, new int[]{0, 0});
        stateMachine.selectComponent("or6");
        stateMachine.selectComponent("output8");

        return stateMachine.finishComponentEditor();
    }

    //for tests
    private static Component createGlobalModuleTestWithStateMachine(com.example.mainpackage.logic.user.User user, Context context) {
        ComponentEditorStateMachine stateMachine = new ComponentEditorStateMachine(ComponentType.PROJECT);
        stateMachine.addSimpleComponent(ComponentType.INPUT, new int[]{4, 6});
        stateMachine.addSimpleComponent(ComponentType.INPUT, new int[]{2, 1});
        stateMachine.addSimpleComponent(ComponentType.INPUT, new int[]{0, 0});
        stateMachine.addSimpleComponent(ComponentType.INPUT, new int[]{0, 0});

        //test
        stateMachine.addSimpleComponent(ComponentType.LOGIC_AND, new int[]{0, 0});

        stateMachine.addModule(context.getFilesDir().getPath() + "/" + "modelTest.blif", user, new int[]{2, 3});

        stateMachine.selectComponent("input10");
        stateMachine.selectComponent("input14");

        stateMachine.selectComponent("input11");
        stateMachine.selectComponent("input15");

        stateMachine.selectComponent("input12");
        stateMachine.selectComponent("input16");

        stateMachine.addModule(context.getFilesDir().getPath() + "/" + "modelTest.blif", user, new int[]{0, 0});

        stateMachine.selectComponent("output21");
        stateMachine.selectComponent("input23");

        stateMachine.selectComponent("input12");
        stateMachine.selectComponent("input24");

        stateMachine.selectComponent("input13");
        stateMachine.selectComponent("input25");

        stateMachine.addSimpleComponent(ComponentType.OUTPUT, new int[]{0, 0});

        stateMachine.selectComponent("output30");
        stateMachine.selectComponent("output32");

        return stateMachine.finishComponentEditor();
    }

    private void setRecyclerView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        String username = LogicController.getInstance().getFacade().getCurrentUsername();
        User user = LogicController.getInstance().getFacade().findUserByUsername(username, this);

        if (user == null) {
            return;
        }

        LiveData<List<FilePath>> filePaths = LogicController.getInstance().getFacade().getAllFilesPathOfUser(user.id, this);

        filePaths.observe(this, new Observer<List<FilePath>>() {
            @Override
            public void onChanged(@Nullable List<FilePath> filePaths) {
                RecyclerView.Adapter adapter = new ProjectsListAdapter(filePaths, ListActivity.this);
                mRecyclerView.setAdapter(adapter);
            }
        });
    }

    private void handleNewProject() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.choose_project_type);

        String normalProject = getString(R.string.simple_project);
        String complexProject = getString(R.string.complex_project);
        String[] options = new String[]{normalProject, complexProject};

        builder.setSingleChoiceItems(options, 0, null);

        builder.setPositiveButton(R.string.action_create, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int pos = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                Intent intent = new Intent(ListActivity.this, EditActivity.class);
                intent.putExtra(EditUtils.IS_SIMPLE_EXTRA, pos == 0);
                startActivity(intent);
            }
        });

        builder.setNegativeButton(R.string.dialog_cancel, null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}