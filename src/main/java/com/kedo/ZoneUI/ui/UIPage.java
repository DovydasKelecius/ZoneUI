package com.kedo.ZoneUI.ui;

import com.kedo.ZoneUI.ui.binding.ClickBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class UIPage {

    private final String uiFile;
    private final List<ClickBinding> clickBindings = new ArrayList<>();

    public UIPage(String uiFile) {
        this.uiFile = uiFile;
    }

    public String getUiFile() {
        return uiFile;
    }

    public List<ClickBinding> getClickBindings() {
        return clickBindings;
    }

    public UIPage onButtonClick(String componentId, UIAction action) {
        clickBindings.add(new ClickBinding(componentId, action));
        return this;
    }
}
