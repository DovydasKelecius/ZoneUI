package com.kedo.ZoneUI;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.kedo.ZoneUI.ui.UIManager;
import com.kedo.ZoneUI.setup.UIInitializer; // Add this import

import javax.annotation.Nonnull;

/**
 * This class serves as the entrypoint for the ZoneUI plugin.
 * Use the setup method to register your UI managers, event listeners, and other core systems.
 */
public class ZoneUI extends JavaPlugin {

    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
    private UIManager uiManager;

    public ZoneUI(@Nonnull JavaPluginInit init) {
        super(init);
        LOGGER.atInfo().log("Initializing " + this.getName() + " version " + this.getManifest().getVersion().toString());
    }

    @Override
    protected void setup() {
        LOGGER.atInfo().log("Setting up plugin " + this.getName());

        uiManager = new UIManager(this);

        // Use UIInitializer to set up UI pages and commands
        UIInitializer initializer = new UIInitializer(uiManager, this);
        initializer.initializeUI();
    }

    public UIManager getUiManager() {
        // Returns the UI Manager
        return uiManager;
    }

}
