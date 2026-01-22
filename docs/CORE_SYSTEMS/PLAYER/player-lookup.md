# PlayerRef & Player Lookup

## PlayerRef - Player Session Handle

`PlayerRef` is the primary handle for accessing a connected player’s session. It provides access to session-specific data and managers.

```java
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;

// Get a player by UUID
PlayerRef player = Universe.get().getPlayer(uuid);

// Access player information
UUID uuid = player.getUuid();
String username = player.getUsername();
String language = player.getLanguage();

// Get the entity reference (if player is in a world)
Ref<EntityStore> entityRef = player.getReference();

// Get the holder (if player is between worlds)
Holder<EntityStore> holder = player.getHolder();

// Additional methods available on PlayerRef
PacketHandler packetHandler = player.getPacketHandler();
ChunkTracker chunkTracker = player.getChunkTracker();
HiddenPlayersManager hiddenPlayers = player.getHiddenPlayersManager();
Transform transform = player.getTransform();
UUID worldUuid = player.getWorldUuid();
```

## Looking Up Players

The `Universe` provides several methods for looking up players.

```java
import com.hypixel.hytale.server.core.NameMatching;
import com.hypixel.hytale.server.core.universe.Universe;

Universe universe = Universe.get();

// By UUID (exact match)
PlayerRef player = universe.getPlayer(uuid);

// By username with matching strategy
PlayerRef playerExact = universe.getPlayerByUsername("PlayerName", NameMatching.EXACT);
PlayerRef playerPartial = universe.getPlayerByUsername("Play", NameMatching.STARTS_WITH);
PlayerRef playerIgnoreCase = universe.getPlayerByUsername("playername", NameMatching.EXACT_IGNORE_CASE);
PlayerRef playerStartsIgnoreCase = universe.getPlayerByUsername("play", NameMatching.STARTS_WITH_IGNORE_CASE);

// Get all connected players
List<PlayerRef> allPlayers = universe.getPlayers();
int playerCount = universe.getPlayerCount();
```

### NameMatching Strategies

*   `EXACT`: Exact string match.
*   `EXACT_IGNORE_CASE`: Exact match ignoring case.
*   `STARTS_WITH`: Match if username starts with the given value.
*   `STARTS_WITH_IGNORE_CASE`: Match if username starts with the given value (case insensitive, this is the **default**).
