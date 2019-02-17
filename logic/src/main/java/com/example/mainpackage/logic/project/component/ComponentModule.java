package com.example.mainpackage.logic.project.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComponentModule extends Component {
    
    private final boolean isProject;
    
    private List<Component> data;
    private List<Component> outputs;
    
    public ComponentModule(String name, boolean isProject) {
        super(name);
        this.isProject = isProject;
        this.data = new ArrayList<>();
        this.outputs = null;
    }
    
    public ComponentModule(String name, List<Component> outputs, boolean isProject) {
        super(name);
        this.data = new ArrayList<>();
        this.outputs = outputs;
        this.isProject = isProject;
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
        if(this.outputs == null)
            this.outputs = new ArrayList<>();
            
        this.outputs.addAll(Arrays.asList(previous));
    }
    
    @Override
    public List<Component> getPrevious() {
        return outputs;
    }
    
    @Override
    public void removePrevious(Component previous) {
        
        for(Component component : outputs)
        {
            if(component.equals(previous))
            {
                outputs.remove(component);
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
        return this.outputs;
    }
    public List<Component> getInputList(){
        List<Component> inputList = new ArrayList();
        for(Component component : data)
        {
            if(component instanceof ComponentInput)
                inputList.add(component);
        }
        return inputList;
    }
    
    @Override
    public boolean getOutput(String outputName) {
        for (Component output : outputs) {
            if (output.getName().equals(outputName)) {
                return output.getOutput(outputName);
            }
        }
        
        throw new IllegalStateException("no output found");
    }

    public List<Component> getData() {
        return data;
    }

    public boolean isIsProject() {
        return isProject;
    }
    
    @Override
    public String toString() {
        return "ComponentModule{" + "isProject=" + isProject + ", data=" + data + ", outputs=" + outputs + '}';
    }

    
}