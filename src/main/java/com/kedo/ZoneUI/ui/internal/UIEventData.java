package com.kedo.ZoneUI.ui.internal;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

// This class is designed to mirror the structure of Hytale's tutorial EventData for specific fields.
// For dynamic data, Hytale's Codec system has limitations with BuilderCodec.
public class UIEventData {

    private String eventName; // Restored
    public String playerName;

    public static final BuilderCodec<UIEventData> CODEC =
            BuilderCodec.builder(UIEventData.class, UIEventData::new)
                    .append(
                            new KeyedCodec<>("EventName", Codec.STRING), // Restored
                            UIEventData::setEventName,
                            UIEventData::getEventName
                    ).add()
                    .append(
                            new KeyedCodec<>("@PlayerName", Codec.STRING),
                            (UIEventData obj, String val) -> obj.playerName = val,
                            (UIEventData obj) -> obj.playerName
                    ).add()
                    .build();

    public String getEventName() { // Restored
        return eventName;
    }

    public void setEventName(String eventName) { // Restored
        this.eventName = eventName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}

