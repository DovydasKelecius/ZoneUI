package com.kedo.ZoneUI.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.kedo.ZoneUI.ZoneUI;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class ZUIModsCommand extends AbstractPlayerCommand {

    private final ZoneUI plugin;

    public ZUIModsCommand(ZoneUI plugin) {
        super("mods", "Opens the Mods Page UI");
        this.plugin = plugin;
    }

    @Override
    protected void execute(@NonNullDecl CommandContext ctx, @NonNullDecl Store<EntityStore> store,
                           @NonNullDecl Ref<EntityStore> ref, @NonNullDecl PlayerRef playerRef, @NonNullDecl World world) {
        Player player = store.getComponent(ref, Player.getComponentType());

        assert player != null;
        plugin.getUiManager().showPage("mods", player, playerRef, ref, store);
    }
}
