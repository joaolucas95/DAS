package com.example.lucas.list;

import com.example.lucas.edit.EditActivity;
import com.example.lucas.edit.EditUtils;
import com.example.lucas.logic.LogicController;
import com.example.lucas.logic.dblogic.FileHistoryViewModel;
import com.example.lucas.logic.dblogic.FilePath;
import com.example.lucas.logic.dblogic.User;
import com.example.lucas.main.R;
import com.example.mainpackage.logic.project.FileManagement.FileType;
import com.example.mainpackage.logic.project.FileManagement.ProjectFileManagement;
import com.example.mainpackage.logic.project.Project;
import com.example.mainpackage.logic.project.component.Component;
import com.example.mainpackage.logic.project.component.ComponentModule;
import com.example.mainpackage.logic.project.component.ComponentType;
import com.example.mainpackage.logic.statemachinepackage.ComponentEditorStateMachine;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    private FileHistoryViewModel mFileHistoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        this.mFileHistoryViewModel = ViewModelProviders.of(this).get(FileHistoryViewModel.class);

        setUiComponents();


/*
        //
        //  example - add file path to user
        //
        String username = LogicController.getInstance().getFacade().getCurrentUsername();
        User user = mFileHistoryViewModel.findUserByUsername(username);
        FilePath filePath = new FilePath("projectxpto", "/projectxpto.bin" , user.id);
        mFileHistoryViewModel.insertFilePath(filePath);

        //
        //  example - remove file path
        //
        //FilePath filePath = mFileHistoryViewModel.findFilePathEntityByFilePath("projetoxpt");
        //if(filePath != null)
            //mFileHistoryViewModel.deleteFilePath(filePath);
*/
        //just a test
        mFileHistoryViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {

                Log.d("test", "------ All users");
                for (User user : users) {
                    Log.d("test", String.valueOf(user));
                    Log.d("test", "FilePath list of user:\n");

                    for (FilePath filePath : mFileHistoryViewModel.findAllFilesPathOfUser(user.id))
                        Log.d("test", String.valueOf(filePath));
                }
                Log.d("test", "---------");
            }
        });
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

    private void createModelTestBin() {
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
            LogicController.getInstance().getFacade().saveProject(project, getApplicationContext().getFilesDir().getPath().toString(), FileType.BLIF);

        } catch (Exception ex) {
            Log.d("test", "Project already created");
        }


        try{
            //for test - test create global module and save as blif file and load
            String projectNameTest = "modelGlobalTest";
            Component model = createGlobalModuleTestWithStateMachine(com.example.mainpackage.logic.user.User.getInstance(), getApplicationContext());

            Project project = new Project(com.example.mainpackage.logic.user.User.getInstance(),projectNameTest);
            project.setComponentModule(model);

            LogicController.getInstance().getFacade().saveProject(project, getApplicationContext().getFilesDir().getPath().toString(), FileType.BLIF);

            project = null;
            project = LogicController.getInstance().getFacade().getProject(getApplicationContext().getFilesDir().getPath().toString() + "/modelGlobalTest.blif");
            Log.d("test", "Project loaded:" + project);

        }catch (Exception ex){
            Log.d("test", "Error:" + ex);
        }

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
    private static Component createGlobalModuleTestWithStateMachine(com.example.mainpackage.logic.user.User user, Context context){
        ComponentEditorStateMachine stateMachine = new ComponentEditorStateMachine(ComponentType.PROJECT);
        stateMachine.addSimpleComponent(ComponentType.INPUT, new int[]{4, 6});
        stateMachine.addSimpleComponent(ComponentType.INPUT, new int[]{2, 1});
        stateMachine.addSimpleComponent(ComponentType.INPUT, new int[]{0, 0});
        stateMachine.addSimpleComponent(ComponentType.INPUT, new int[]{0, 0});

        //test
        stateMachine.addSimpleComponent(ComponentType.LOGIC_AND, new int[]{0, 0});

        stateMachine.addModule(context.getFilesDir().getPath().toString() + "/" + "modelTest.blif", user, new int[]{2, 3});

        stateMachine.selectComponent("input10");
        stateMachine.selectComponent("input14");

        stateMachine.selectComponent("input11");
        stateMachine.selectComponent("input15");

        stateMachine.selectComponent("input12");
        stateMachine.selectComponent("input16");

        stateMachine.addModule(context.getFilesDir().getPath().toString() + "/" + "modelTest.blif", user, new int[]{0, 0});

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

        String username = LogicController.getInstance().getFacade().getCurrentUsername();
        User user = mFileHistoryViewModel.findUserByUsername(username);

        if (user != null) {
            mFileHistoryViewModel.getAllFilesPathOfUser(user.id).observe(this, new Observer<List<FilePath>>() {
                @Override
                public void onChanged(@Nullable List<FilePath> filePaths) {

                    //Log.d("test", "projects of user: " + String.valueOf(filePaths));

                    RecyclerView recyclerView;
                    RecyclerView.Adapter mAdapter;
                    RecyclerView.LayoutManager layoutManager;

                    recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

                    // use this setting to improve performance if you know that changes
                    // in content do not change the layout size of the RecyclerView
                    recyclerView.setHasFixedSize(true);

                    // use a linear layout manager
                    layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);

                    // specify an adapter (see also next example)
                    mAdapter = new ProjectsListAdapter(filePaths, getApplicationContext());
                    recyclerView.setAdapter(mAdapter);
                }
            });
        }
    }

    private void handleNewProject() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.choose_project_type);

        String normalProject = getString(R.string.simple_project);
        String complexProject = getString(R.string.complex_project);
        String[] options = new String[]{normalProject, complexProject};

        int checkedItem = 0;
        builder.setSingleChoiceItems(options, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing.
            }
        });

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