package com.example.lucas.simulation;

import com.example.lucas.logic.LogicController;
import com.example.lucas.main.R;
import com.example.mainpackage.logic.dblogic.FilePath;
import com.example.mainpackage.logic.project.Project;
import com.example.mainpackage.logic.project.component.Component;
import com.example.mainpackage.logic.project.component.ComponentModule;
import com.example.mainpackage.logic.project.filemanagement.FileType;
import com.example.mainpackage.logic.project.tests.Combination;
import com.example.mainpackage.logic.project.tests.Signal;
import com.example.mainpackage.logic.utils.Config;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SimulationActivity extends AppCompatActivity {

    private FilePath filePath;
    private Project project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);

        try {
            if (savedInstanceState == null) {
                filePath = (FilePath) getIntent().getExtras().getSerializable("filePath");
                Log.d("test", "FilePath in simulation activity:" + filePath);
            }
            this.project = LogicController.getInstance().getFacade().getProject(filePath.filePath);
            Log.d("test", "Project loaded:" + project);
            setUiComponents();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUiComponents() {
        setupToolbar();
        setupCloseAndSaveButton();
        setupNewCombinationButton();
        setupDeleteCombinationButton();
        setupRunCombinationsButton();
        setupInputs(); //if the project has combinations create their layout...
    }

    private void setupDeleteCombinationButton() {
        Button button = findViewById(R.id.button_delete_combination);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (project.getSignalForSimulation() == null || project.getSignalForSimulation().getCombinations().isEmpty())
                    return;

                project.getSignalForSimulation().getCombinations().remove(project.getSignalForSimulation().getCombinations().size() - 1);

                LinearLayout combinationsListLinearLayout = findViewById(R.id.combination_list);
                View viewtmp = combinationsListLinearLayout.getChildAt(combinationsListLinearLayout.getChildCount() - 1);
                combinationsListLinearLayout.removeView(viewtmp);
            }
        });
    }

    private void setupCloseAndSaveButton() {
        Button button = findViewById(R.id.button_close_save_simulation);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    if (filePath.filePath.contains(".bin"))
                        LogicController.getInstance().getFacade().saveProject(project, false, Config.BASE_FILE_PATH, FileType.BINARY);
                    else
                        LogicController.getInstance().getFacade().saveProject(project, false, Config.BASE_FILE_PATH, FileType.BLIF);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                finish();
            }
        });
    }

    private void setupNewCombinationButton() {
        Button button = findViewById(R.id.button_new_combination);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Map<String, Boolean> testtmp = new LinkedHashMap<>();
                ComponentModule module = (ComponentModule) project.getComponentModule();

                List<Component> inputList = module.getInputList();

                for (Component input : inputList) {
                    testtmp.put(input.getName(), false);
                }

                for (int i = inputList.size() - 1; i >= 0; i--) {
                    testtmp.put(inputList.get(i).getName(), false);

                }

                if (project.getSignalForSimulation() == null)
                    project.setSignalForSimulation(new Signal());

                Combination combination = new Combination(testtmp);

                project.getSignalForSimulation().getCombinations().add(combination);

                LinearLayout combinationsListLinearLayout = findViewById(R.id.combination_list);
                LinearLayout combinationLinearLayout = createCombination(combination);
                combinationsListLinearLayout.addView(combinationLinearLayout);
            }
        });
    }

    private void setupRunCombinationsButton() {
        Button button = findViewById(R.id.button_run_combinations);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                List<Combination> results = project.runSimulation();
                LinearLayout resultsLinearLayour = findViewById(R.id.result_list);

                //clear the linear layout
                resultsLinearLayour.removeAllViews();

                if (project.getSignalForSimulation().getCombinations().isEmpty()) {
                    TextView tv = new TextView(getApplicationContext());
                    tv.setText("Need to define combinations first.");
                    tv.setTextColor(Color.parseColor("#000000"));
                    resultsLinearLayour.addView(tv);
                }

                for (int i = 0; i < project.getSignalForSimulation().getCombinations().size(); i++) {
                    Combination result = results.get(i);

                    TextView tv = new TextView(getApplicationContext());
                    tv.setText("Result");
                    tv.setTextColor(Color.parseColor("#000000"));
                    resultsLinearLayour.addView(tv);

                    Iterator it = result.getValues().entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry) it.next();
                        tv = new TextView(getApplicationContext());
                        tv.setTextColor(Color.parseColor("#000000"));
                        tv.setText("" + (String) pair.getKey() + ": " + pair.getValue());
                        resultsLinearLayour.addView(tv);
                    }
                }
            }
        });
    }

    private void setupInputs() {

        LinearLayout combinationsListLinearLayout = findViewById(R.id.combination_list);

        if (project.getSignalForSimulation() == null)
            return;

        for (Combination combination : project.getSignalForSimulation().getCombinations()) {
            LinearLayout combinationLinearLayout = createCombination(combination);
            combinationsListLinearLayout.addView(combinationLinearLayout);
        }
    }

    private LinearLayout createCombination(Combination combinationTmp) {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        Iterator it = combinationTmp.getValues().entrySet().iterator();
        while (it.hasNext()) {
            final Map.Entry pair = (Map.Entry) it.next();

            TextView tv = new TextView(getApplicationContext());
            tv.setTextColor(Color.parseColor("#000000"));
            tv.setText((String) pair.getKey());
            linearLayout.addView(tv);

            final ToggleButton toggleButton = new ToggleButton(this);
            toggleButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    pair.setValue(isChecked);
                }
            });
            toggleButton.setChecked((Boolean) pair.getValue());
            linearLayout.addView(toggleButton);
        }

        return linearLayout;
    }

    private void setupToolbar() {
        //TODO...
    }
}
