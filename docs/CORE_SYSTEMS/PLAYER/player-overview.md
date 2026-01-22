# Player Management & Persistence Overview

This guide covers the Hytale player management and persistence systems, including how to look up players, store custom player data, and handle player events.

## Architecture Overview
```
Universe
├── PlayerRef (connected players map)
├── PlayerStorage (data persistence)
└── World
    ├── Players (world-specific player map)
    └── EntityStore (ECS for player entities)
```

**Player Data Flow:**
`PlayerStorage.load() -> Holder<EntityStore> -> World.addPlayer() -> Ref<EntityStore>`

## Best Practices

*   **Use PlayerRef for session data**: Keep runtime state in `PlayerRef`, persistent state in `PlayerConfigData`.
*   **Check entity reference validity**: Always verify `playerRef.getReference() != null` before accessing entity state; `playerRef.isValid()` checks both reference and holder.
*   **Handle async operations**: Player storage and world transfer operations are async; use `CompletableFuture` properly.
*   **Mark data as changed**: Call `markChanged()` on `PlayerConfigData` when modifying persistent fields (setter methods already call this).
*   **Use world-keyed events**: Register for specific worlds (`AddPlayerToWorldEvent`, `DrainPlayerFromWorldEvent`, `PlayerReadyEvent`) when possible for better performance.
*   **Clean up on disconnect**: Remove temporary data in `PlayerDisconnectEvent` handlers (not world-keyed).
*   **Respect player state**: Check if a player is in a world (`getReference()` returns `Ref`) vs between worlds (`getHolder()` returns `Holder`).
*   **Handle transfer failures**: World transfers can fail; always handle exceptions in `CompletableFuture` chains.
*   **Use correct event registration**: `PlayerConnectEvent` and `PlayerDisconnectEvent` are NOT keyed events; world events (`AddPlayerToWorld`, `DrainPlayerFromWorld`, `PlayerReady`) ARE keyed by world name.
*   **Deprecated API awareness**: `PlayerRef.getComponent()` is deprecated; use holder/store component access instead.
