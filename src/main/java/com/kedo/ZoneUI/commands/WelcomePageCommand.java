package com.kedo.ZoneUI.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.kedo.ZoneUI.pages.WelcomePage;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class WelcomePageCommand extends AbstractPlayerCommand {

  public WelcomePageCommand() {
    super("welcome", "Opens the WelcomePage UI");
  }

  @Override
  protected void execute(@NonNullDecl CommandContext ctx, @NonNullDecl Store<EntityStore> store,
                         @NonNullDecl Ref<EntityStore> ref, @NonNullDecl PlayerRef playerRef, @NonNullDecl World world) {
    Player player = store.getComponent(ref, Player.getComponentType());

    WelcomePage page = new WelcomePage(playerRef);

    assert player != null;
    player.getPageManager().openCustomPage(ref, store, page);
  }
}
