package com.kedo.ZoneUI.ui;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.kedo.ZoneUI.ui.internal.InternalUIPage;

import java.util.HashMap;
import java.util.Map;

public class UIManager {

    private final JavaPlugin plugin;
    private final Map<String, UIPage> pages = new HashMap<>();

    public UIManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerPage(String pageId, UIPage page) {
        pages.put(pageId, page);
    }

    public UIPage getPage(String pageId) {
        return pages.get(pageId);
    }

    public void showPage(String pageId, Player player, PlayerRef playerRef, Ref<EntityStore> ref, Store<EntityStore> store) {
        UIPage page = pages.get(pageId);
        if (page != null) {
            InternalUIPage internalPage = new InternalUIPage(playerRef, page); // Removed data parameter
            player.getPageManager().openCustomPage(ref, store, internalPage);
        }
    }

    public void closePage(Player player, Ref<EntityStore> ref, Store<EntityStore> store) {
        player.getPageManager().setPage(ref, store, com.hypixel.hytale.protocol.packets.interface_.Page.None);
    }

    public UIManager getUIManager() {
      return null;
    }
}
