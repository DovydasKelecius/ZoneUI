package com.kedo.ZoneUI;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.kedo.ZoneUI.commands.WelcomePageCommand;
import com.kedo.ZoneUI.ui.UIData;
import com.kedo.ZoneUI.ui.UIPage;
import com.kedo.ZoneUI.ui.UIManager;
import com.hypixel.hytale.server.core.Message;

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

        UIPage welcomePage = new UIPage("Pages/WelcomePage.ui")
                .onButtonClick("#GreetButton", (player, ref, store, data) -> {
                    String playerName = data.playerName != null && !data.playerName.isEmpty() ? data.playerName : "Stranger";
                    player.sendMessage(Message.raw("Hello, " + playerName + "!"));
                    uiManager.closePage(player, ref, store);
                });
        uiManager.registerPage("welcome", welcomePage);


        getCommandRegistry().registerCommand(new WelcomePageCommand(this));

        LOGGER.atInfo().log("Commands Registered: /welcome");
    }

    public UIManager getUiManager() {
        // Returns the UI Manager
        return uiManager;
    }

}
