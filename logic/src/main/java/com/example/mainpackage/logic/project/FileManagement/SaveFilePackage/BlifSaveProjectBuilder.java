package com.example.mainpackage.logic.project.FileManagement.SaveFilePackage;

import com.example.mainpackage.logic.project.Project;
import com.example.mainpackage.logic.project.component.Component;
import com.example.mainpackage.logic.project.component.ComponentInput;
import com.example.mainpackage.logic.project.component.ComponentModule;
import com.example.mainpackage.logic.project.component.ComponentSimple;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BlifSaveProjectBuilder extends SaveProjectBuilder{

    @Override
    public boolean saveProject(String filePathString, Project project) {

        try {
            List<Component> modulesToPrint = new ArrayList<>();

            List<String> content;

            content = printModuleBlifFormat((ComponentModule) project.getComponentModule(), modulesToPrint);

            for(Component component : modulesToPrint){
                content.addAll(printModuleBlifFormat((ComponentModule) component, modulesToPrint));
            }
            //Path file = Paths.get(filePathString + "/" + project.getName() + ".blif");
            //Files.write(file, content, Charset.forName("UTF-8"));


            String strContent="";
            for(String str : content){
                strContent+= str +"\n";
            }

            String path = filePathString + "/" + project.getName() + ".blif";
            File file = new File(path);
            FileOutputStream fos;
            try {
                file.getParentFile().mkdirs();

                fos = new FileOutputStream(file);
                fos.write(strContent.getBytes());
                fos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (Exception ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
    }


    private static List<String> printModuleBlifFormat(ComponentModule module, List<Component> modulesToPrint) {
        List<String> content = new ArrayList();

        content.add(".model " + module.getName() + "-" + module.getPosition()[0] + ";" + module.getPosition()[1]);

        String inputsString = ".inputs";
        for(Component input : module.getInputList())
            inputsString += " " + input.getName() + "-" + input.getPosition()[0] + ";" + input.getPosition()[1];
        content.add(inputsString);

        String outputsString = ".outputs";
        for(Component output : module.getOutputList())
            outputsString += " " + output.getName() + "-" + output.getPosition()[0] + ";" + output.getPosition()[1];
        content.add(outputsString);

        String connection ="\n";

        for(Component component : module.getData())
        {
            //if is a module
            if(component instanceof ComponentModule)
            {
                modulesToPrint.add(component);

                ComponentModule moduleTmp = (ComponentModule) component;
                //add annotation
                connection += ".subckt " + moduleTmp.getName() + "-" + moduleTmp.getPosition()[0] + ";" + moduleTmp.getPosition()[1];

                //for all module inputs will print its name and its previous components names
                for(Component inputOfModule : moduleTmp.getInputList())
                {
                    connection += " " + inputOfModule.getName() + "=";
                    for(Component ctemp : inputOfModule.getPrevious())
                        connection += ctemp.getName();

                }

                //for each output
                for(Component ouputOfModule : moduleTmp.getOutputList()){
                    connection += " " + ouputOfModule.getName() + "=";

                    //find where it is referenced in the model's data
                    for(Component mainComponentTmp : module.getData()){
                        if(mainComponentTmp instanceof ComponentModule) {
                            for (Component tmpComponent : ((ComponentModule) mainComponentTmp).getData()) {
                                if(verifyIfHasPreviousWithThatName(tmpComponent, ouputOfModule)){
                                    connection += tmpComponent.getName();
                                }
                            }
                        }
                        else
                        {
                            //when is a simple component just verify
                            if(verifyIfHasPreviousWithThatName(mainComponentTmp, ouputOfModule)){
                                connection += mainComponentTmp.getName();
                            }
                        }
                    }
                }

                connection +="\n";
            }
            //if not input...
            else if(!(component instanceof ComponentInput))
            {
                connection += ".names";
                String results ="";

                List<Component> previousList;

                //if the component is a componentModule the previous components are their output elements... So we need get his previous list by his inputs
                if(component instanceof ComponentModule)
                {
                    previousList = new ArrayList();
                    for(Component componenttmp : ((ComponentModule)component).getInputList())
                    {
                        previousList.addAll(componenttmp.getPrevious());
                    }
                }
                else
                {
                    previousList = component.getPrevious();
                }
                for(Component previous : previousList)
                    connection += " " + previous.getName() + "-" + previous.getPosition()[0] + ";" + previous.getPosition()[1];

                connection += " " + component.getName() + "-" + component.getPosition()[0] + ";" + component.getPosition()[1] + "\n";
                connection += ((ComponentSimple)component).getLogicGates();
            }
        }

        content.add(connection);
        content.add(".end \n\n");

        return content;
    }

    private static boolean verifyIfHasPreviousWithThatName(Component mainComponentTmp, Component ouputOfModule) {

        if(mainComponentTmp.getPrevious() != null) {
            for (Component previous : mainComponentTmp.getPrevious()) {
                if (previous.getName().equals(ouputOfModule.getName()))
                    return true;
            }
        }
        return false;
    }
    
}
