package com.kedo.ZoneUI.ui;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.kedo.ZoneUI.ui.internal.UIEventData;

@FunctionalInterface
public interface UIAction {
    void execute(Player player, Ref<EntityStore> ref, Store<EntityStore> store, UIEventData data);
}
