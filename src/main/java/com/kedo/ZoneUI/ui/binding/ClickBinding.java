package com.kedo.ZoneUI.ui.binding;

import com.kedo.ZoneUI.ui.UIAction;

public class ClickBinding {

    private final String componentId;
    private final UIAction action;

    public ClickBinding(String componentId, UIAction action) {
        this.componentId = componentId;
        this.action = action;
    }

    public String getComponentId() {
        return componentId;
    }

    public UIAction getAction() {
        return action;
    }
}
