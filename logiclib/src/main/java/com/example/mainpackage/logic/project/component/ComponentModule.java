package com.example.mainpackage.logic.project.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComponentModule extends Component {
    
    private final boolean isProject;
    
    private List<Component> data;
    
    public ComponentModule(String name, boolean isProject, int[] position) {
        super(name, position);
        this.isProject = isProject;
        this.data = new ArrayList<>();
    }

    public void addComponent(Component component) {
        this.data.add(component);
    }
    
    public void addComponent(List<Component> component) {
        this.data.addAll(component);
    }
    
    public Component getComponent(String name){
        for(Component component: data){
            if(component.getName().equals(name))
                return component;
        }
        throw new IllegalStateException("no component found");
    }
    
    @Override
    public void setPrevious(Component... previous) {
        //As this component is a Module... Will be add output components...
        data.addAll(Arrays.asList(previous));
    }
    
    @Override
    public List<Component> getPrevious() {
        return getOutputList();
    }
    
    @Override
    public void removePrevious(Component previous) {
        List<Component> outputs = getOutputList();
        for(Component component : outputs)
        {
            if(component.equals(previous))
            {
                data.remove(component); //TODO: need to be tested
                return;
            }
        }
    }

    @Override
    public void setInput(String name, boolean value) {
        for (Component component : data) {
            if (component.getName().equals(name)) {
                ((ComponentInput) component).setValue(value);
                return;
            }
        }
        throw new IllegalStateException("no input found");
    }
    
    public List<Component> getOutputList() {
        List<Component> outputList = new ArrayList<>();
        for(Component component : data)
        {
            if(component instanceof ComponentOutput)
                outputList.add(component);
        }
        return outputList;
    }
    public List<Component> getInputList(){
        List<Component> inputList = new ArrayList<>();
        for(Component component : data)
        {
            if(component instanceof ComponentInput)
                inputList.add(component);
        }
        return inputList;
    }
    
    @Override
    public boolean getOutput(String outputName) {
        List<Component> outputs = getOutputList();
        for (Component output : outputs) {
            if (output.getName().equals(outputName)) {
                return output.getOutput(outputName);
            }
        }
        
        throw new IllegalStateException("no output found");
    }

    @Override
    public ComponentType getType() {
        return isProject ? ComponentType.PROJECT : ComponentType.MODULE;
    }

    public List<Component> getData() {
        return data;
    }

    public boolean isIsProject() {
        return isProject;
    }
    
    @Override
    public String toString() {
        return "ComponentModule{" + "isProject=" + isProject + ", data=" + data + '}';
    }

    
}