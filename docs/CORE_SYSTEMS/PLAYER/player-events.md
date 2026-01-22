# Player Events

The Hytale server provides a robust event system for tracking player lifecycle, including connecting, disconnecting, and moving between worlds.

## Connection Events

These events are fired when a player joins or leaves the server entirely. They are **not** keyed by world.

```java
import com.hypixel.hytale.server.core.event.events.player.*;

 @Override
protected void setup() {
    EventRegistry events = getEventRegistry();

    // Player connecting to server (before entering world)
    // Note: This is NOT a keyed event - use the non-keyed registration
    events.register(PlayerConnectEvent.class, event -> {
        PlayerRef playerRef = event.getPlayerRef();
        Holder<EntityStore> holder = event.getHolder();
        World targetWorld = event.getWorld();

        // You can redirect the player to a different world
        event.setWorld(Universe.get().getWorld("lobby"));

        // Access the Player component from the holder
        // Note: The event.getPlayer() method is deprecated
        Player player = holder.getComponent(Player.getComponentType());
    });

    // Player disconnected from server
    // Note: This is NOT a keyed event - use the non-keyed registration
    events.register(PlayerDisconnectEvent.class, event -> {
        PlayerRef playerRef = event.getPlayerRef();
        PacketHandler.DisconnectReason reason = event.getDisconnectReason();
        getLogger().at(Level.INFO).log(playerRef.getUsername() + " disconnected: " + reason);
    });
}
```

## World Events

These events are fired when a player enters or leaves a specific world. They are **keyed by world name**.

```java
// In your setup() method:
EventRegistry events = getEventRegistry();

// Player added to a world (keyed by world name)
events.register(AddPlayerToWorldEvent.class, "world_name", event -> {
    Holder<EntityStore> holder = event.getHolder();
    World world = event.getWorld();

    // You can suppress the "Player has joined the game" message
    event.setBroadcastJoinMessage(false);
});

// Player removed from a world (keyed by world name)
events.register(DrainPlayerFromWorldEvent.class, "world_name", event -> {
    Holder<EntityStore> holder = event.getHolder();
    World world = event.getWorld();
    Transform transform = event.getTransform();

    // You can redirect the player to a different world upon leaving
    event.setWorld(Universe.get().getWorld("hub"));
    event.setTransform(new Transform(0, 64, 0));
});

// Player ready to receive gameplay data (keyed by world name)
events.register(PlayerReadyEvent.class, "world_name", event -> {
    Player player = event.getPlayer();
    Ref<EntityStore> ref = event.getPlayerRef();
    int readyId = event.getReadyId(); // Increments each time the player becomes ready

    // This is the safest point to send initial game state, UI, etc. to the player.
});
```
