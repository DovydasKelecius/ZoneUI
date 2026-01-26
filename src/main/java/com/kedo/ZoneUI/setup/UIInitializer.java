package com.kedo.ZoneUI.setup;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.PluginBase; // Import PluginBase
import com.hypixel.hytale.server.core.plugin.PluginManager;
import com.kedo.ZoneUI.commands.ZUICommand;
import com.kedo.ZoneUI.ui.UIPage;
import com.kedo.ZoneUI.ui.UIManager;
import com.hypixel.hytale.server.core.Message;

import java.util.ArrayList;
import java.util.List;

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

        // --- Mods Page Setup ---
        UIPage modsPage = new UIPage("Pages/zuiMods.ui")
                .onShow((player, uiCommandBuilder) -> {
                    List<PluginBase> loadedPlugins = PluginManager.get().getPlugins(); // Corrected API call type
                    List<PluginBase> plugins = new ArrayList<>(loadedPlugins); // Convert to List for indexed access
                    
                    for (int i = 1; i <= 2; i++) {
                        String modId = "mod" + String.format("%04d", i);
                        if (i <= plugins.size()) {
                            PluginBase p = plugins.get(i - 1);
                            uiCommandBuilder.set("#" + modId + "group.Visible", true);
                            uiCommandBuilder.set("#" + modId + "descriptiontext.Text", p.getName());
                        } else {
                            uiCommandBuilder.set("#" + modId + "group.Visible", false);
                        }
                    }
                });
        uiManager.registerPage("mods", modsPage);


        // --- Command Registration ---
        plugin.getCommandRegistry().registerCommand(new ZUICommand((com.kedo.ZoneUI.ZoneUI) plugin));
        plugin.getLogger().atInfo().log("Commands Registered: /zui welcome, /zui mods");
    }
}
