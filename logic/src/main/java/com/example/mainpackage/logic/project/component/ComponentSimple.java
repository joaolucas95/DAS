package com.example.mainpackage.logic.project.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ComponentSimple extends Component {
    
    protected List<Component> previous;
    
    public ComponentSimple(String name) {
        super(name);
    }    
    
    @Override
    public void setPrevious(Component... previous) {
        
        if(this.previous == null)
            this.previous = new ArrayList<>();
            
        this.previous.addAll(Arrays.asList(previous));
    }
    
    @Override
    public void removePrevious(Component previous) {
        
        for(Component component : this.previous)
        {
            if(component.equals(previous))
            {
                this.previous.remove(component);
                return;
            }
        }
    }
    
    @Override
    public void setInput(String name, boolean value) {
        throw new IllegalStateException("trying to set input in a simple component");
    }

    @Override
    public List<Component> getPrevious() {
        return previous;
    }

    
    
}