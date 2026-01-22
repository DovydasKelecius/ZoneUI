# GUI System

Hytale’s server-side GUI system is composed of three distinct subsystems, each designed for different use cases. All three are managed per-player and accessed through the Player component.

## Architecture Overview

**Player Component**
├── WindowManager      - Inventory-based UIs (containers, crafting)
├── PageManager        - Custom dialogs and overlays
├── HudManager         - Persistent on-screen elements
├── HotbarManager      - Player hotbar slot management
└── WorldMapTracker    - World map UI state

**Page Classes**
├── CustomUIPage            - Base class for custom pages
├── BasicCustomUIPage       - Simple pages without event handling
└── InteractiveCustomUIPage - Pages with typed event data handling

**HUD Classes**
└── CustomUIHud             - Base class for custom HUD overlays

**UI Building Tools**
├── UICommandBuilder   - Build UI commands (set values, append elements)
├── UIEventBuilder     - Bind UI events to server callbacks
└── EventData          - Pass parameters with events

**UI Assets**
├── .ui files          - Text-based layout definitions
├── Common.ui          - Global styles and constants
└── Pages/*.ui         - Page-specific layouts and components

## The Three GUI Systems

### Windows System
*   **Use Case**: Inventory-based UIs for containers, crafting benches, and processing stations.
*   **Functionality**: Windows display item grids and handle player-item interactions.
*   **Examples**: Chests, crafting tables, furnaces.

### Pages System
*   **Use Case**: Custom dialogs, menus, and full-screen overlays.
*   **Functionality**: Build fully interactive UIs with event handling for shops, dialogs, and custom interfaces.
*   **Examples**: Shops, dialogs, settings menus.

### HUD System
*   **Use Case**: Persistent on-screen elements that players see during gameplay.
*   **Functionality**: Control elements like health bars, hotbar, compass, quest trackers, and custom overlays.
*   **Examples**: Health bars, compass, quest tracker.

## UI Building Tools

`UICommandBuilder` and `UIEventBuilder` are used for creating and updating UI elements dynamically from your plugin code. `EventData` is used to pass parameters with events.

## Accessing GUI Managers

All three primary GUI managers, along with additional UI-related managers, are accessed through the `Player` component.

```java
// Get the Player component from an entity reference
Player playerComponent = store.getComponent(ref, Player.getComponentType());

// Access the managers
WindowManager windowManager = playerComponent.getWindowManager();
PageManager pageManager = playerComponent.getPageManager();
HudManager hudManager = playerComponent.getHudManager();

// Additional UI-related managers
HotbarManager hotbarManager = playerComponent.getHotbarManager();
WorldMapTracker worldMapTracker = playerComponent.getWorldMapTracker();

// Reset managers (HUD, windows, camera, movement, world map tracker)
playerComponent.resetManagers(holder);
```

## When to Use Each System

| System  | Use Case                      | Examples                          |
|---------|-------------------------------|-----------------------------------|
| Windows | Item-based interactions       | Chests, crafting tables, furnaces  |
| Pages   | Full-screen UIs               | Shops, dialogs, settings menus    |
| HUD     | Always-visible info           | Health bars, compass, quest tracker |

## Quick Start Examples

### Opening a Custom Page
This example shows a simple page that does not handle user interaction directly.

```java
public class MyCustomPage extends BasicCustomUIPage {
    public MyCustomPage(PlayerRef playerRef) {
        super(playerRef, CustomPageLifetime.CanDismiss);
    }

    @Override
    public void build(UICommandBuilder commands) { // Note: build method signature
        commands.append("Pages/MyPage.ui");
        commands.set("#title", "Welcome!");
    }
}

// Example usage:
// pageManager.openCustomPage(ref, store, new MyCustomPage(playerRef));
```

### Interactive Pages and Event Handling
For pages that require user interaction, you'll use `InteractiveCustomUIPage` and define `EventData` to handle data sent from the client.

```java
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

import javax.annotation.Nonnull;

public class Tutorial2Page extends InteractiveCustomUIPage<Tutorial2Page.GreetEventData> {

    /**
     * EventData class - holds the data we receive when the button is clicked.
     * The CODEC tells Hytale:
     * 1. How to create a new GreetEventData instance
     * 2. How to fill in the playerName field from the UI
     */
    public static class GreetEventData {
        public String playerName;

        public static final BuilderCodec<GreetEventData> CODEC =
                BuilderCodec.builder(GreetEventData.class, GreetEventData::new)
                        .append(
                                // " @PlayerName" = read from UI input (the @ prefix is important!)
                                new KeyedCodec<>(" @PlayerName", Codec.STRING),
                                // Setter: put the value into obj.playerName
                                (GreetEventData obj, String val) -> obj.playerName = val,
                                // Getter: read the value from obj.playerName
                                (GreetEventData obj) -> obj.playerName
                        )
                        .add()
                        .build();
    }

    public Tutorial2Page( @Nonnull PlayerRef playerRef) {
        // Pass the CODEC to the parent class
        super(playerRef, CustomPageLifetime.CanDismissOrCloseThroughInteraction, GreetEventData.CODEC);
    }

    @Override
    public void build(
            @Nonnull Ref<EntityStore> ref,
            @Nonnull UICommandBuilder cmd,
            @Nonnull UIEventBuilder evt,
            @Nonnull Store<EntityStore> store
    ) {
        // Load the UI file
        cmd.append("Pages/Tutorial2Page.ui");

        // Bind the button click event
        // When #GreetButton is clicked:
        // - Read the value from #NameInput.Value
        // - Put it in " @PlayerName" (which maps to GreetEventData.playerName)
        evt.addEventBinding(
                CustomUIEventBindingType.Activating,
                "#GreetButton",
                new EventData().append(" @PlayerName", "#NameInput.Value")
        );
    }

    @Override
    public void handleDataEvent(
            @Nonnull Ref<EntityStore> ref,
            @Nonnull Store<EntityStore> store,
            @Nonnull GreetEventData data
    ) {
        // Get the player component
        Player player = store.getComponent(ref, Player.getComponentType());

        // Use the data from the form
        String name = data.playerName != null && !data.playerName.isEmpty()
                ? data.playerName
                : "Stranger";

        // Send a greeting message
        playerRef.sendMessage(Message.raw("Hello, " + name + "!"));

        // Close the UI
        player.getPageManager().setPage(ref, store, Page.None);
    }
}
```

### Corresponding `Tutorial2Page.ui` File
This `.ui` file defines the layout for the `Tutorial2Page`, including a text input field and a button. It also demonstrates how to include common UI styles from another `.ui` file using `$C = "../Common.ui";`.

```
$C = "../Common.ui"; // Include common styles from the parent directory

@GreetButtonStyle = TextButtonStyle(
    Default: (Background: #3a7bd5, LabelStyle: (FontSize: 16, TextColor: #ffffff, RenderBold: true, HorizontalAlignment: Center, VerticalAlignment: Center)),
    Hovered: (Background: #4a8be5, LabelStyle: (FontSize: 16, TextColor: #ffffff, RenderBold: true, HorizontalAlignment: Center, VerticalAlignment: Center)),
    Pressed: (Background: #2a6bc5, LabelStyle: (FontSize: 16, TextColor: #ffffff, RenderBold: true, HorizontalAlignment: Center, VerticalAlignment: Center))
);

Group {
    Anchor: (Width: 400, Height: 280);
    Background: #1a1a2e(0.95);
    LayoutMode: Top;
    Padding: (Full: 20);

    Label #Title {
        Text: "Tutorial Level 2";
        Anchor: (Height: 40);
        Style: (FontSize: 24, TextColor: #ffffff, HorizontalAlignment: Center);
    }

    Label #Subtitle {
        Text: "Events - TextField + Button";
        Anchor: (Height: 25);
        Style: (FontSize: 14, TextColor: #888888, HorizontalAlignment: Center);
    }

    Group { Anchor: (Height: 10); }

    Label #Prompt {
        Text: "Enter your name:";
        Anchor: (Height: 25);
        Style: (FontSize: 16, TextColor: #cccccc);
    }

    $C.@TextField #NameInput { // Using a TextField style from Common.ui
        Anchor: (Height: 40);
        PlaceholderText: "Type your name here...";
    }

    Group { Anchor: (Height: 10); }

    TextButton #GreetButton {
        Anchor: (Height: 45);
        Text: "Greet Me!";
        Style: @GreetButtonStyle;
    }
}
```

### Showing a Custom HUD
This example demonstrates how to define and show a custom HUD overlay that can display dynamic values.

```java
public class BossHealthHud extends CustomUIHud {
    public BossHealthHud(PlayerRef playerRef) {
        super(playerRef);
    }

    @Override
    protected void build(UICommandBuilder builder) {
        builder.append("#hud-root", "ui/boss_health.ui");
        builder.set("#boss-name", "Dragon");
        builder.set("#health-bar", 1.0f);
    }
}

// Example usage:
// hudManager.setCustomHud(playerRef, new BossHealthHud(playerRef));
```

### Modifying HUD Components
You can dynamically control the visibility of built-in HUD components.

```java
// Show only essential components
hudManager.setVisibleHudComponents(playerRef,
    HudComponent.Hotbar,
    HudComponent.Health,
    HudComponent.Reticle
);

// Hide specific components
hudManager.hideHudComponents(playerRef,
    HudComponent.Compass,
    HudComponent.ObjectivePanel
);
```

## UI File System

Hytale uses `.ui` files as the client-side layout format. These text-based assets define UI structure, styles, and components.

### Server UI Assets (built-in)
├── Common.ui                   # Global styles and variables
├── Common/
│   └── TextButton.ui          # Reusable components
└── Pages/
    ├── DialogPage.ui          # NPC dialogs
    ├── ShopPage.ui            # Shop interfaces
    └── RespawnPage.ui         # Death/respawn screen

### Plugin Asset Pack Structure (your plugin)
```
src/main/resources/
├── manifest.json              # Set "IncludesAssetPack": true
└── Common/
    └── UI/
        └── Custom/
            ├── MyPage.ui          # Custom .ui files
            └── MyBackground.png   # Textures
```

### Creating Custom .ui Files
To create custom UI layouts with images in your plugin:

1.  Set `"IncludesAssetPack": true` in `manifest.json`.
2.  Place `.ui` files in `src/main/resources/Common/UI/Custom/`.
3.  Reference them in Java as `Custom/MyPage.ui`.
4.  Use `PatchStyle(TexturePath: "image.png")` for loading textures (paths are relative to the `.ui` file).
5.  See Custom Pages and UI Building Tools for detailed examples.

## Package References

| Class                     | Package                                                |
|---------------------------|--------------------------------------------------------|
| `WindowManager`           | `com.hypixel.hytale.server.core.entity.entities.player.windows` |
| `PageManager`             | `com.hypixel.hytale.server.core.entity.entities.player.pages`   |
| `HudManager`              | `com.hypixel.hytale.server.core.entity.entities.player.hud`     |
| `HotbarManager`           | `com.hypixel.hytale.server.core.entity.entities.player`         |
| `WorldMapTracker`         | `com.hypixel.hytale.server.core.universe.world`        |
| `UICommandBuilder`        | `com.hypixel.hytale.server.core.ui.builder`             |
| `UIEventBuilder`          | `com.hypixel.hytale.server.core.ui.builder`             |
| `EventData`               | `com.hypixel.hytale.server.core.ui.builder`             |
| `Player`                  | `com.hypixel.hytale.server.core.entity.entities`         |
| `BasicCustomUIPage`       | `com.hypixel.hytale.server.core.entity.entities.player.pages`   |
| `CustomUIPage`            | `com.hypixel.hytale.server.core.entity.entities.player.pages`   |
| `InteractiveCustomUIPage` | `com.hypixel.hytale.server.core.entity.entities.player.pages`   |
| `CustomUIHud`             | `com.hypixel.hytale.server.core.entity.entities.player.hud`     |
| `HudComponent`            | `com.hypixel.hytale.protocol.packets.interface_`       |
| `CustomPageLifetime`      | `com.hypixel.hytale.protocol.packets.interface_`       |
