package com.kedo.ZoneUI.setup;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.kedo.ZoneUI.commands.WelcomePageCommand;
import com.kedo.ZoneUI.ui.UIPage;
import com.kedo.ZoneUI.ui.UIManager;
import com.hypixel.hytale.server.core.Message;

public class UIInitializer {

    private final UIManager uiManager;
    private final JavaPlugin plugin;

    public UIInitializer(UIManager uiManager, JavaPlugin plugin) {
        this.uiManager = uiManager;
        this.plugin = plugin;
    }

    public void initializeUI() {
        // --- Welcome Page Setup ---
        UIPage welcomePage = new UIPage("Pages/WelcomePage.ui")
                .onButtonClick("#GreetButton", (player, ref, store, data) -> {
                    String playerName = data.playerName != null && !data.playerName.isEmpty() ? data.playerName : "Stranger";
                    player.sendMessage(Message.raw("Hello, " + playerName + "!"));
                    uiManager.closePage(player, ref, store);
                });
        uiManager.registerPage("welcome", welcomePage);

        // --- Command Registration ---
        plugin.getCommandRegistry().registerCommand(new WelcomePageCommand((com.kedo.ZoneUI.ZoneUI) plugin));
        plugin.getLogger().atInfo().log("Commands Registered: /welcome");
    }
}
