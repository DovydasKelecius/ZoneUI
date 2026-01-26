package com.kedo.ZoneUI.ui;

import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.kedo.ZoneUI.ui.binding.ClickBinding;
import com.hypixel.hytale.server.core.entity.entities.Player;


import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;


public class UIPage {

    private final String uiFile;
    private final List<ClickBinding> clickBindings = new ArrayList<>();
    private BiConsumer<Player, UICommandBuilder> onShow;

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

    public UIPage onShow(BiConsumer<Player, UICommandBuilder> onShow) {
        this.onShow = onShow;
        return this;
    }

    public BiConsumer<Player, UICommandBuilder> getOnShow() {
        return onShow;
    }
}
