# Player Entity Component

When a player is in a world, their data is represented by a `Player` entity component. This component extends `LivingEntity` and provides access to world-specific data and managers.

## Accessing the Player Component

You can get the `Player` component from a `PlayerRef`'s entity reference.

```java
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.protocol.GameMode;

// From PlayerRef (when player is in a world)
Ref<EntityStore> ref = playerRef.getReference();
if (ref != null) {
    Store<EntityStore> store = ref.getStore();
    Player player = store.getComponent(ref, Player.getComponentType());

    // Access player-specific data
    GameMode gameMode = player.getGameMode();
    Inventory inventory = player.getInventory();
    int viewRadius = player.getViewRadius();
    int clientViewRadius = player.getClientViewRadius();
    boolean isFirstSpawn = player.isFirstSpawn();
}
```

## Player Managers

The `Player` component is the gateway to various UI and data managers.

```java
// Access various player managers through the Player component
WindowManager windowManager = player.getWindowManager();
PageManager pageManager = player.getPageManager();
HudManager hudManager = player.getHudManager();
HotbarManager hotbarManager = player.getHotbarManager();
WorldMapTracker worldMapTracker = player.getWorldMapTracker();
```
