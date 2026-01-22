package com.kedo.ZoneUI;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.kedo.ZoneUI.commands.WelcomePageCommand;


import javax.annotation.Nonnull;

/**
 * This class serves as the entrypoint for the ZoneUI plugin.
 * Use the setup method to register your UI managers, event listeners, and other core systems.
 */
public class ZoneUI extends JavaPlugin {

    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    public ZoneUI(@Nonnull JavaPluginInit init) {
        super(init);
        LOGGER.atInfo().log("Initializing " + this.getName() + " version " + this.getManifest().getVersion().toString());
    }

    @Override
    protected void setup() {
        LOGGER.atInfo().log("Setting up plugin " + this.getName());

        getCommandRegistry().registerCommand(new WelcomePageCommand());

        LOGGER.atInfo().log("Commands Registered: /welcome");
    }
}
