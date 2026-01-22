package com.kedo.ZoneUI.pages;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBindingType;
import com.hypixel.hytale.protocol.packets.interface_.Page;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.pages.InteractiveCustomUIPage;
import com.hypixel.hytale.server.core.ui.builder.EventData;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import javax.annotation.Nonnull;

public class WelcomePage extends InteractiveCustomUIPage<WelcomePage.WelcomeEventData> {

  /**
   * EventData class - holds the data we receive when the button is clicked.
   *
   * The CODEC tells Hytale:
   * 1. How to create a new GreetEventData instance
   * 2. How to fill in the playerName field from the UI
   */
  public static class WelcomeEventData {
    public String playerName;

  public static final BuilderCodec<WelcomeEventData> CODEC =
      BuilderCodec.builder(WelcomeEventData.class, WelcomeEventData::new)
          .append(
              // "@PlayerName" = read from UI input (the @ prefix is important!)
              new KeyedCodec<>("@PlayerName", Codec.STRING),
              // Setter: put the value into obj.playerName
              (WelcomeEventData obj, String val) -> obj.playerName = val,
              // Getter: read the value from obj.playerName
              (WelcomeEventData obj) -> obj.playerName
          ).add()
          .build();
  }


  public WelcomePage(@NonNullDecl PlayerRef playerRef){
    super(playerRef, CustomPageLifetime.CanDismissOrCloseThroughInteraction, WelcomeEventData.CODEC);
  }

  @Override
  public void build(
      @NonNullDecl Ref<EntityStore> ref,
      @NonNullDecl UICommandBuilder cmd,
      @NonNullDecl UIEventBuilder evt,
      @NonNullDecl Store<EntityStore> store
  ) {
    // Load the UI file
    cmd.append("Pages/WelcomePage.ui");

    // Bind the button click event
    // When #GreetButton is clicked:
    // - Read the value from #NameInput.Value
    // - Put it in "@PlayerName" (which maps to WelcomeEventData.playerName)
    evt.addEventBinding(
        CustomUIEventBindingType.Activating,
        "#GreetButton",
        new EventData().append("@PlayerName", "#NameInput.Value")
    );
  }

  @Override
  public void handleDataEvent(
      @NonNullDecl Ref<EntityStore> ref,
      @NonNullDecl Store<EntityStore> store,
      WelcomeEventData data
  ) {
    // Get the player component
    Player player = store.getComponent(ref, Player.getComponentType());

    // Use the data from the form
    String name = data.playerName != null ?
        data.playerName  : "Stranger";

    // Send a greeting message
    playerRef.sendMessage(Message.raw("Hello, " + name + "!"));

    // Close the UI
    assert player != null;
    player.getPageManager().setPage(ref, store, Page.None);
  }


}

