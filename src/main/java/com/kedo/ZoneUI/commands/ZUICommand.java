package com.kedo.ZoneUI.commands;


import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
import com.kedo.ZoneUI.ZoneUI;

public class ZUICommand extends AbstractCommandCollection {

    public ZUICommand(ZoneUI plugin) {
        super("zui", "ZoneUI commands");
        addSubCommand(new ZUIWelcomeCommand(plugin));
        addSubCommand(new ZUIModsCommand(plugin));
    }
}
