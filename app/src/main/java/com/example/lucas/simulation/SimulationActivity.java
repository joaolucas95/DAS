package com.example.lucas.simulation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.lucas.logic.LogicController;
import com.example.lucas.logic.dblogic.FilePath;
import com.example.lucas.main.R;
import com.example.mainpackage.ILogicFacade;
import com.example.mainpackage.Main;
import com.example.mainpackage.logic.project.FileManagement.ProjectFileManagement;
import com.example.mainpackage.logic.project.Project;
import com.example.mainpackage.logic.project.component.Component;
import com.example.mainpackage.logic.project.component.ComponentType;
import com.example.mainpackage.logic.statemachinepackage.ComponentEditorStateMachine;
import com.example.mainpackage.logic.user.User;
import com.example.mainpackage.logic.utils.Config;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SimulationActivity extends AppCompatActivity {

    private FilePath filePath;
    private Project project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);

        if (savedInstanceState == null) {
            filePath = (FilePath) getIntent().getExtras().getSerializable("filePath");
            Log.d("test", "FilePath in simulation:" + filePath);
        }

        setUiComponents();

        String projectName = "modelTest";

        Component model = createModelTestWithStateMachine();
        Project project = new Project(User.getInstance(), projectName);
        project.setComponentModule(model);
        this.project = project;
        Log.d("test", "Project created:" + project);

        try {
            ProjectFileManagement projectFileManagement = new ProjectFileManagement();
            boolean result = projectFileManagement.saveProject(project, getApplicationContext().getFilesDir().getPath().toString() + "/", Config.FILE_TYPE_BLIF);

            Log.d("test", "SAVED:" + result);


        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }



        this.project = LogicController.getInstance().getFacade().getProject(getApplicationContext().getFilesDir().getPath().toString() + "/" + filePath.filePath);
        Log.d("test", "Project loaded:" + project);

    }

    private static Component createModelTestWithStateMachine(){
        ComponentEditorStateMachine stateMachine = new ComponentEditorStateMachine(ComponentType.MODULE);
        stateMachine.addSimpleComponent(ComponentType.INPUT);
        stateMachine.addSimpleComponent(ComponentType.INPUT);
        stateMachine.addSimpleComponent(ComponentType.INPUT);

        stateMachine.addSimpleComponent(ComponentType.LOGIC_AND);

        //test
        stateMachine.selectComponent("and4");
        stateMachine.selectComponent("and4");

        stateMachine.selectComponent("input1");
        stateMachine.selectComponent("and4");
        stateMachine.selectComponent("input2");
        stateMachine.selectComponent("and4");

        stateMachine.addSimpleComponent(ComponentType.LOGIC_AND);
        stateMachine.selectComponent("input2");
        stateMachine.selectComponent("and5");
        stateMachine.selectComponent("input3");
        stateMachine.selectComponent("and5");

        stateMachine.addSimpleComponent(ComponentType.LOGIC_OR);
        stateMachine.selectComponent("and4");
        stateMachine.selectComponent("or6");
        stateMachine.selectComponent("and5");
        stateMachine.selectComponent("or6");

        stateMachine.addSimpleComponent(ComponentType.OUTPUT);
        stateMachine.selectComponent("and4");
        stateMachine.selectComponent("output7");

        stateMachine.addSimpleComponent(ComponentType.OUTPUT);
        stateMachine.selectComponent("or6");
        stateMachine.selectComponent("output8");

        return stateMachine.finishComponentEditor();
    }

    private void setUiComponents() {
        setupToolbar();
    }

    private void setupToolbar() {
        //TODO...
    }
}
