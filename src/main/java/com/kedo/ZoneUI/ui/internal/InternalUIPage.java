package com.kedo.ZoneUI.ui.internal;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBindingType;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.pages.InteractiveCustomUIPage;
import com.hypixel.hytale.server.core.ui.builder.EventData;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.kedo.ZoneUI.ui.UIData;
import com.kedo.ZoneUI.ui.UIPage;
import com.kedo.ZoneUI.ui.binding.ClickBinding;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class InternalUIPage extends InteractiveCustomUIPage<UIEventData> {

    private final UIPage page;
    // Removed initialData field

    public InternalUIPage(PlayerRef playerRef, UIPage page) { // Removed initialData from constructor
        super(playerRef, CustomPageLifetime.CanDismissOrCloseThroughInteraction, UIEventData.CODEC);
        this.page = page;
    }

    @Override
    public void build(@NonNullDecl Ref<EntityStore> ref, @NonNullDecl UICommandBuilder cmd, @NonNullDecl UIEventBuilder evt, @NonNullDecl Store<EntityStore> store) {
        cmd.append(page.getUiFile());
        // Removed initialData cmd.set() loop

        for (ClickBinding binding : page.getClickBindings()) {
            EventData eventData = new EventData();
            eventData.append("EventName", binding.getComponentId()); // Restore EventName

            // Directly append " @PlayerName" as per working example
            eventData.append("@PlayerName", "#NameInput.Value"); // Hardcoded for this specific example

            evt.addEventBinding(
                    CustomUIEventBindingType.Activating,
                    binding.getComponentId(),
                    eventData
            );
        }
    }

    @Override
    public void handleDataEvent(@NonNullDecl Ref<EntityStore> ref, @NonNullDecl Store<EntityStore> store, UIEventData data) {
        Player player = store.getComponent(ref, Player.getComponentType());

        for (ClickBinding binding : page.getClickBindings()) {
            if (binding.getComponentId().equals(data.getEventName())) { // Restore check
                binding.getAction().execute(player, ref, store, data); // Restore data parameter
                break;
            }
        }
    }
}
