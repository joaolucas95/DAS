package com.example.mainpackage.logic.project.component;

import java.util.ArrayList;
import java.util.List;

public class ComponentUtils {

    private ComponentUtils() {
        // Do nothing.
    }

    public static List<ComponentType> getComponentsTypes() {
        ComponentType[] components = ComponentType.values();

        List<ComponentType> aux = new ArrayList<>();
        for (ComponentType type: components) {
            if (type == ComponentType.PROJECT) {
                continue;
            }

            aux.add(type);
        }

        return aux;
    }

    public static String getComponentName(ComponentType type) {
        switch (type) {
            case INPUT:
                return "Input";

            case MODULE:
                return "Module";

            case OUTPUT:
                return "Output";

            case PROJECT:
                return "Project";

            case LOGIC_OR:
                return "OR logical";

            case LOGIC_AND:
                return "AND logical";

            default:
                throw new IllegalStateException("illegal type:" + type);
        }
    }
}
