# Storing Custom Player Data

Hytale provides two primary ways to store persistent custom data for players: using the built-in `PlayerConfigData` component, or creating your own custom components.

## Using PlayerConfigData

`PlayerConfigData` stores a variety of persistent player information across sessions and worlds.

```java
import com.hypixel.hytale.server.core.entity.entities.player.data.PlayerConfigData;
import com.hypixel.hytale.server.core.entity.entities.player.data.PlayerWorldData;

Player player = /* get player component */;
PlayerConfigData config = player.getPlayerConfigData();

// Global player data
String currentWorld = config.getWorld();
Set<String> knownRecipes = config.getKnownRecipes();
Set<String> discoveredZones = config.getDiscoveredZones();
Set<UUID> discoveredInstances = config.getDiscoveredInstances();
Object2IntMap<String> reputationData = config.getReputationData();
Set<UUID> activeObjectiveUUIDs = config.getActiveObjectiveUUIDs();
String preset = config.getPreset();

// Per-world player data
PlayerWorldData worldData = config.getPerWorldData("world_name");
Transform lastPosition = worldData.getLastPosition();
boolean isFirstSpawn = worldData.isFirstSpawn();
SavedMovementStates lastMovementStates = worldData.getLastMovementStates();
MapMarker[] worldMapMarkers = worldData.getWorldMapMarkers();
PlayerRespawnPointData[] respawnPoints = worldData.getRespawnPoints();
List<PlayerDeathPositionData> deathPositions = worldData.getDeathPositions();

// Mark data as changed to trigger save
// Note: Many setter methods call this automatically.
config.markChanged();
```

## Adding Custom Components

For more complex or structured custom data, you can define and register your own persistent components.

### 1. Define Your Component

Create a class that implements `Component<EntityStore>` and defines its serialization using a `BuilderCodec`.

```java
// Define your custom component
public class MyPlayerData implements Component<EntityStore> {
    public static final BuilderCodec<MyPlayerData> CODEC = BuilderCodec.builder(
        MyPlayerData.class,
        MyPlayerData::new
    )
    .addField(new KeyedCodec<>("customField", Codec.STRING),
        (data, value) -> data.customField = value,
        data -> data.customField)
    .build();

    private String customField;

    public String getCustomField() { return customField; }
    public void setCustomField(String value) {
        this.customField = value;
    }

    @Override
    public Component<EntityStore> clone() {
        MyPlayerData copy = new MyPlayerData();
        copy.customField = this.customField;
        return copy;
    }
}
```

### 2. Register the Component

In your plugin's `setup()` method, register your new component with the entity store registry.

```java
 @Override
protected void setup() {
    ComponentType<EntityStore, MyPlayerData> myDataType =
        getEntityStoreRegistry().registerComponent(
            MyPlayerData.class,
            MyPlayerData::new
        );
}
```

### 3. Use the Component

You can now access and modify your custom component data through the player's `Holder`.

```java
// Use the component
Holder<EntityStore> holder = playerRef.getHolder();
MyPlayerData data = holder.ensureAndGetComponent(myDataType);
data.setCustomField("value");
```
