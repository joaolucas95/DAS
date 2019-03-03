package com.example.mainpackage.logic.project.filemanagement.savefilepkg;

import com.example.mainpackage.logic.project.component.Component;
import com.example.mainpackage.logic.project.component.ComponentInput;
import com.example.mainpackage.logic.project.component.ComponentModule;
import com.example.mainpackage.logic.project.component.ComponentSimple;
import com.example.mainpackage.logic.project.tests.Combination;
import com.example.mainpackage.logic.project.tests.Signal;
import com.example.mainpackage.logic.project.tests.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BlifSaveProjectBuilder extends SaveProjectBuilder {

    @Override
    public boolean saveProject() {
        try {
            List<Component> modulesToPrint = new ArrayList<>();

            List<String> content = printModuleBlifFormat((ComponentModule) project.getComponentModule(), modulesToPrint);

            for (Component component : modulesToPrint) {
                content.addAll(printModuleBlifFormat((ComponentModule) component, modulesToPrint));
            }

            printSimulationsAndTests(content, project.getSignalForSimulation(), project.getTests());

            StringBuilder strContent = new StringBuilder();
            for (String str : content) {
                strContent.append(str).append("\n");
            }

            String path = filePathString + "/" + project.getName() + ".blif";
            File file = new File(path);
            FileOutputStream fos;
            file.getParentFile().mkdirs();

            fos = new FileOutputStream(file);
            fos.write(strContent.toString().getBytes());
            fos.close();

        } catch (Exception ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
    }

    private void printSimulationsAndTests(List<String> content, Signal signalForSimulation, List<Test> tests) {
        List<String> simulationAndTestsContent = new ArrayList<>();

        int indexOfEndComponent = getIndexOfEndComponent(content);

        simulationAndTestsContent.add(getSimulationContent(signalForSimulation));

        simulationAndTestsContent.add(getTestsContent(tests));

        if (indexOfEndComponent == -1)
            return;

        content.addAll(indexOfEndComponent, simulationAndTestsContent);
    }

    private String getTestsContent(List<Test> tests) {
        StringBuilder content = new StringBuilder();

        for (Test test : tests) {
            StringBuilder testStr = new StringBuilder(".test ");

            for (Combination combination : test.getSignalInput().getCombinations()) {
                testStr.append(getCombinationContent(combination));
            }

            testStr.append("/ ");

            for (Combination combination : test.getSignalExpected().getCombinations()) {
                testStr.append(getCombinationContent(combination));
            }

            content.append(testStr).append("\n");
        }

        return content.toString();
    }

    private int getIndexOfEndComponent(List<String> content) {
        for (int i = 0; i < content.size(); i++) {
            if (content.get(i).contains(".end"))
                return i;
        }
        return -1;
    }

    private String getSimulationContent(Signal signalForSimulation) {
        StringBuilder content = new StringBuilder(".simulation ");

        for (Combination combination : signalForSimulation.getCombinations()) {
            content.append(getCombinationContent(combination));
            content.append("; ");
        }
        return content.toString();
    }

    private String getCombinationContent(Combination combination) {
        StringBuilder content = new StringBuilder();

        Iterator it = combination.getValues().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            content.append(pair.getKey()).append("=").append(pair.getValue()).append(" ");
        }

        return content.toString();
    }

    private static List<String> printModuleBlifFormat(ComponentModule module, List<Component> modulesToPrint) {
        List<String> content = new ArrayList<>();

        content.add(".model " + module.getName() + "-" + module.getPosition()[0] + ";" + module.getPosition()[1]);

        StringBuilder inputsString = new StringBuilder(".inputs");
        for (Component input : module.getInputList())
            inputsString.append(" ").append(input.getName()).append("-").append(input.getPosition()[0]).append(";").append(input.getPosition()[1]);
        content.add(inputsString.toString());

        StringBuilder outputsString = new StringBuilder(".outputs");
        for (Component output : module.getOutputList())
            outputsString.append(" ").append(output.getName()).append("-").append(output.getPosition()[0]).append(";").append(output.getPosition()[1]);
        content.add(outputsString.toString());

        StringBuilder connection = new StringBuilder("\n");

        for (Component component : module.getData()) {
            //if is a module
            if (component instanceof ComponentModule) {
                modulesToPrint.add(component);

                ComponentModule moduleTmp = (ComponentModule) component;
                //add annotation
                connection.append(".subckt ").append(moduleTmp.getName()).append("-").append(moduleTmp.getPosition()[0]).append(";").append(moduleTmp.getPosition()[1]);

                //for all module inputs will print its name and its previous components names
                for (Component inputOfModule : moduleTmp.getInputList()) {
                    connection.append(" ").append(inputOfModule.getName()).append("=");
                    for (Component ctemp : inputOfModule.getPrevious())
                        connection.append(ctemp.getName());

                }

                //for each output
                for (Component outputOfModule : moduleTmp.getOutputList()) {
                    connection.append(" ").append(outputOfModule.getName()).append("=");

                    //find where it is referenced in the model's data
                    for (Component mainComponentTmp : module.getData()) {
                        if (mainComponentTmp instanceof ComponentModule) {
                            for (Component tmpComponent : ((ComponentModule) mainComponentTmp).getData()) {
                                if (verifyIfHasPreviousWithThatName(tmpComponent, outputOfModule)) {
                                    connection.append(tmpComponent.getName());
                                }
                            }
                        } else {
                            //when is a simple component just verify
                            if (verifyIfHasPreviousWithThatName(mainComponentTmp, outputOfModule)) {
                                connection.append(mainComponentTmp.getName());
                            }
                        }
                    }
                }

                connection.append("\n");
            }
            //if not input...
            else if (!(component instanceof ComponentInput)) {
                connection.append(".names");

                List<Component> previousList = component.getPrevious();

                for (Component previous : previousList)
                    connection.append(" ").append(previous.getName()).append("-").append(previous.getPosition()[0]).append(";").append(previous.getPosition()[1]);

                connection.append(" ").append(component.getName()).append("-").append(component.getPosition()[0]).append(";").append(component.getPosition()[1]).append("\n");
                connection.append(((ComponentSimple) component).getLogicGates());
            }
        }

        content.add(connection.toString());
        content.add(".end \n\n");

        return content;
    }

    private static boolean verifyIfHasPreviousWithThatName(Component mainComponentTmp, Component outputOfModule) {
        if (mainComponentTmp.getPrevious() != null) {
            for (Component previous : mainComponentTmp.getPrevious()) {
                if (previous.getName().equals(outputOfModule.getName()))
                    return true;
            }
        }
        return false;
    }

}
