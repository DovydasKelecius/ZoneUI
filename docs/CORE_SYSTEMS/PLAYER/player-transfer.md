# Player Transfer Between Worlds & Servers

This guide covers how to programmatically move players between different worlds within the same server, and how to refer them to a different server.

## Moving Players Between Worlds

Player transfers are asynchronous operations. You can add a player to a different world using the `World.addPlayer()` method, which returns a `CompletableFuture`.

```java
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.math.vector.Transform;

// Get the target world and define a spawn point
World targetWorld = Universe.get().getWorld("adventure");
Transform spawnPoint = new Transform(100, 64, 200);

// Add player to new world and handle the async result
targetWorld.addPlayer(playerRef, spawnPoint)
    .thenAccept(resultPlayerRef -> {
        getLogger().at(Level.INFO).log("Player transferred to " + targetWorld.getName());
    })
    .exceptionally(error -> {
        getLogger().at(Level.SEVERE).log("Transfer failed: " + error.getMessage());
        return null; // Handle the error
    });

/*
Available overloads:
- addPlayer(PlayerRef playerRef) - Uses the world's default spawn provider for position.
- addPlayer(PlayerRef playerRef, Transform transform) - Moves player to an explicit position.
- addPlayer(PlayerRef playerRef, Transform transform, Boolean clearWorldOverride, Boolean fadeInOutOverride) - Provides finer control over the transfer process.
*/
```

## Server Referral

You can transfer a player to an entirely different server using `PlayerRef.referToServer()`.

### Simple Referral
```java
// Refer player to another server
playerRef.referToServer("other.server.com", 25565);
```

### Referral with Payload
You can also include a custom data payload, which can be used for passing authentication tokens or other information between servers.
```java
// With custom data payload (max 4096 bytes)
byte[] transferData = /* your custom data */;
playerRef.referToServer("other.server.com", 25565, transferData);
```
