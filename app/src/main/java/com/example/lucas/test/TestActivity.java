package com.example.lucas.test;

import com.example.lucas.logic.LogicController;
import com.example.lucas.main.R;
import com.example.mainpackage.logic.dblogic.FilePath;
import com.example.mainpackage.logic.project.Combination;
import com.example.mainpackage.logic.project.FileManagement.FileType;
import com.example.mainpackage.logic.project.Project;
import com.example.mainpackage.logic.project.Signal;
import com.example.mainpackage.logic.project.Test;
import com.example.mainpackage.logic.project.component.Component;
import com.example.mainpackage.logic.project.component.ComponentModule;
import com.example.mainpackage.logic.utils.Config;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TestActivity extends AppCompatActivity {

    private FilePath filePath;
    private Project project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        try {
            if (savedInstanceState == null) {
                filePath = (FilePath) getIntent().getExtras().getSerializable("filePath");
                Log.d("test", "FilePath in test activity:" + filePath);
            }
            this.project = LogicController.getInstance().getFacade().getProject(filePath.filePath);
            Log.d("test", "Project loaded:" + project);
            setUiComponents();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUiComponents() {

        setupCloseAndSaveTestsButton();
        setupNewTestButton();
        setupDeleteTestButton();
        setupTests(); //if the project has tests create their layout...

    }

    private void setupTests() {

        for (Test test : project.getTests()) {

            RelativeLayout testRelativeLayout = getNewTestLinearLayout(test);

            LinearLayout testsLinearLayout = findViewById(R.id.ll_tests);

            setupInputAndExpectedCombinations(testRelativeLayout, test);

            testsLinearLayout.addView(testRelativeLayout);

            Space emptySpace = new Space(TestActivity.this);
            emptySpace.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    30));
            testsLinearLayout.addView(emptySpace);
        }
    }

    private void setupInputAndExpectedCombinations(RelativeLayout testRelativeLayout, Test test) {

        LinearLayout inputCombinationsListLinearLayout = testRelativeLayout.findViewById(R.id.input_combination_list_test);
        LinearLayout expectedCombinationsListLinearLayout = testRelativeLayout.findViewById(R.id.expected_combination_list_test);

        for (Combination combination : test.getSignalInput().getCombinations()) {
            LinearLayout combinationLinearLayout = createCombination(combination);
            inputCombinationsListLinearLayout.addView(combinationLinearLayout);
        }

        for (Combination combination : test.getSignalExpected().getCombinations()) {
            LinearLayout combinationLinearLayout = createCombination(combination);
            expectedCombinationsListLinearLayout.addView(combinationLinearLayout);
        }
    }

    private void setupDeleteTestButton() {
        Button button = findViewById(R.id.btn_remove_test);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (project.getTests() == null || project.getTests().isEmpty())
                    return;

                project.getTests().remove(project.getTests().size() - 1);

                LinearLayout testsListLinearLayout = findViewById(R.id.ll_tests);
                View testLinearLayout = testsListLinearLayout.getChildAt(testsListLinearLayout.getChildCount() - 1);
                View space = testsListLinearLayout.getChildAt(testsListLinearLayout.getChildCount() - 2);
                testsListLinearLayout.removeView(testLinearLayout);
                testsListLinearLayout.removeView(space);
            }
        });
    }

    private void setupNewTestButton() {
        Button button = findViewById(R.id.btn_new_test);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                List<Combination> combinationsInput = new ArrayList();
                List<Combination> combinationsExpected = new ArrayList();

                Signal signalInput = new Signal(combinationsInput);
                Signal signalExpected = new Signal(combinationsExpected);
                Test test = new Test(signalInput, signalExpected);
                project.addTest(test);

                RelativeLayout testRelativeLayout = getNewTestLinearLayout(test);

                LinearLayout testsLinearLayout = findViewById(R.id.ll_tests);
                testsLinearLayout.addView(testRelativeLayout);

                Space emptySpace = new Space(TestActivity.this);
                emptySpace.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        30));
                testsLinearLayout.addView(emptySpace);

            }
        });
    }

    private RelativeLayout getNewTestLinearLayout(Test test) {
        List<Combination> combinationsInput = test.getSignalInput().getCombinations();
        List<Combination> combinationsExpected = test.getSignalExpected().getCombinations();

        RelativeLayout testRelativeLayout = (RelativeLayout) LayoutInflater.from(TestActivity.this).inflate(R.layout.test_layout, null);

        //for input combinations
        setupNewInputCombinationButton(testRelativeLayout, combinationsInput);
        setupDeleteInputCombinationButton(testRelativeLayout, test);

        //for expected combinations
        setupNewExpectedCombinationButton(testRelativeLayout, combinationsExpected);
        setupDeleteExpectedCombinationButton(testRelativeLayout, test);

        setupRunTestButton(testRelativeLayout, test);
        //setupInputs(testRelativeLayout); //if the project has combinations create their layout...


        return testRelativeLayout;
    }

    private void setupDeleteExpectedCombinationButton(final RelativeLayout testRelativeLayout, final Test test) {
        Button button = testRelativeLayout.findViewById(R.id.btn_delete_expected_combination_test);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (test.getSignalExpected().getCombinations() == null || test.getSignalExpected().getCombinations().isEmpty())
                    return;

                test.getSignalExpected().getCombinations().remove(test.getSignalExpected().getCombinations().size() - 1);

                LinearLayout combinationsListLinearLayout = testRelativeLayout.findViewById(R.id.expected_combination_list_test);
                View viewtmp = combinationsListLinearLayout.getChildAt(combinationsListLinearLayout.getChildCount() - 1);
                combinationsListLinearLayout.removeView(viewtmp);
            }
        });
    }

    private void setupDeleteInputCombinationButton(final RelativeLayout testRelativeLayout, final Test test) {
        Button button = testRelativeLayout.findViewById(R.id.btn_delete_input_combination_test);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (test.getSignalInput().getCombinations() == null || test.getSignalInput().getCombinations().isEmpty())
                    return;

                test.getSignalInput().getCombinations().remove(test.getSignalInput().getCombinations().size() - 1);

                LinearLayout combinationsListLinearLayout = testRelativeLayout.findViewById(R.id.input_combination_list_test);
                View viewtmp = combinationsListLinearLayout.getChildAt(combinationsListLinearLayout.getChildCount() - 1);
                combinationsListLinearLayout.removeView(viewtmp);
            }
        });
    }

    private void setupRunTestButton(final RelativeLayout testRelativeLayout, final Test test) {
        Button button = testRelativeLayout.findViewById(R.id.button_run_test);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                LinearLayout resultsLinearLayour = testRelativeLayout.findViewById(R.id.result_list_test);

                String result = "Result: ";

                try {
                    boolean resultTest = project.runTest(test);
                    result += "The test " + (resultTest == true ? "passed" : "did not pass");

                } catch (Exception ex) {
                    result += "An error occored. " + ex.getMessage();
                }

                TextView tv = new TextView(getApplicationContext());
                tv.setText(result);
                resultsLinearLayour.removeAllViews();
                resultsLinearLayour.addView(tv);
            }
        });
    }

    private void setupNewExpectedCombinationButton(final RelativeLayout testLinearLayout, final List<Combination> combinationsExpected) {
        Button button = testLinearLayout.findViewById(R.id.btn_new_expected_combination_test);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Map<String, Boolean> testtmp = new LinkedHashMap<>();
                ComponentModule module = (ComponentModule) project.getComponentModule();
                for (Component output : module.getOutputList()) {
                    testtmp.put(output.getName(), false);
                }

                Combination combination = new Combination(testtmp);

                combinationsExpected.add(combination);

                LinearLayout combinationsListLinearLayout = testLinearLayout.findViewById(R.id.expected_combination_list_test);
                LinearLayout combinationLinearLayout = createCombination(combination);
                combinationsListLinearLayout.addView(combinationLinearLayout);
            }
        });
    }

    private void setupNewInputCombinationButton(final RelativeLayout testLinearLayout, final List<Combination> combinationsInput) {
        Button button = testLinearLayout.findViewById(R.id.btn_new_input_combination_test);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Map<String, Boolean> testtmp = new LinkedHashMap<>();
                ComponentModule module = (ComponentModule) project.getComponentModule();
                for (Component input : module.getInputList()) {
                    testtmp.put(input.getName(), false);
                }

                Combination combination = new Combination(testtmp);

                combinationsInput.add(combination);

                LinearLayout combinationsListLinearLayout = testLinearLayout.findViewById(R.id.input_combination_list_test);
                LinearLayout combinationLinearLayout = createCombination(combination);
                combinationsListLinearLayout.addView(combinationLinearLayout);
            }
        });
    }

    private LinearLayout createCombination(Combination combinationTmp) {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        Iterator it = combinationTmp.getValues().entrySet().iterator();
        while (it.hasNext()) {
            final Map.Entry pair = (Map.Entry) it.next();

            TextView tv = new TextView(getApplicationContext());

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


    private void setupCloseAndSaveTestsButton() {
        Button button = findViewById(R.id.btn_close_save_tests);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    if (filePath.filePath.contains(".bin"))
                        LogicController.getInstance().getFacade().saveProject(project, false, Config.BASE_FILE_PATH, FileType.BINARY);
                    //else
                    //LogicController.getInstance().getFacade().saveProject(project, false, Config.BASE_FILE_PATH, FileType.BLIF);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                finish();
            }
        });
    }
}
