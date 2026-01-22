# Command Arguments

The command system supports several types of arguments, which are registered in your command's constructor using fluent builder methods.

## Required Arguments

Required arguments are positional and must be provided by the user in the order they are registered.

```java
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;

public class GiveCommand extends AbstractCommand {

    private final RequiredArg<String> playerArg;
    private final RequiredArg<String> itemArg;
    private final RequiredArg<Integer> amountArg;

    public GiveCommand() {
        super("give", "Give items to a player");

        // Register required arguments (order matters)
        playerArg = withRequiredArg("player", "Target player", ArgTypes.STRING);
        itemArg = withRequiredArg("item", "Item ID", ArgTypes.STRING);
        amountArg = withRequiredArg("amount", "Quantity", ArgTypes.INTEGER);
    }

    @Override @Nullable
    protected CompletableFuture<Void> execute( @Nonnull CommandContext context) {
        // Retrieve argument values from the context
        String player = playerArg.get(context);
        String item = itemArg.get(context);
        int amount = amountArg.get(context);

        context.sender().sendMessage(
            Message.raw("Gave " + amount + "x " + item + " to " + player)
        );
        return CompletableFuture.completedFuture(null);
    }
}
```

## Optional Arguments

Optional arguments are named and use the `--name <value>` syntax. They are not required for the command to execute.

```java
import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;

public class TeleportCommand extends AbstractCommand {

    // ... required args for x, y, z
    private final OptionalArg<String> worldArg;

    public TeleportCommand() {
        super("tp", "Teleport to coordinates");
        // ...
        
        // Optional argument: --world <name>
        worldArg = withOptionalArg("world", "Target world", ArgTypes.STRING);
    }

    @Override @Nullable
    protected CompletableFuture<Void> execute( @Nonnull CommandContext context) {
        // ... get required args

        // Check if optional arg was provided and get its value
        String world = worldArg.provided(context) ? worldArg.get(context) : "default";

        // Teleport implementation
        return CompletableFuture.completedFuture(null);
    }
}
```

## Default Arguments

Default arguments are like optional arguments but have a fallback value if not provided by the user.

```java
import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.IntArgumentType;

public class SpawnCommand extends AbstractCommand {

    private final DefaultArg<Integer> radiusArg;

    public SpawnCommand() {
        super("spawn", "Spawn entities");

        // Default argument: --radius <value> (defaults to 10)
        radiusArg = withDefaultArg("radius", "Spawn radius",
            IntArgumentType.ranged(1, 100), 10, "10 blocks");
    }

    @Override @Nonnull
    protected CompletableFuture<Void> execute( @Nonnull CommandContext context) {
        // context.get() returns the default value (10) if the arg is not specified
        int radius = context.get(radiusArg);
        
        // Implementation
        return CompletableFuture.completedFuture(null);
    }
}
```

## Flag Arguments

Flags are boolean switches that don’t take a value (e.g., `--verbose`). Their presence evaluates to `true`.

```java
import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;

public class DebugCommand extends AbstractCommand {

    private final FlagArg verboseFlag;
    private final FlagArg allFlag;

    public DebugCommand() {
        super("debug", "Toggle debug mode");

        // Boolean flags: --verbose or --all
        verboseFlag = withFlagArg("verbose", "Enable verbose output");
        allFlag = withFlagrag("all", "Show all information");
    }

    @Override @Nonnull
    protected CompletableFuture<Void> execute( @Nonnull CommandContext context) {
        // Use context.has() to check if a flag was provided
        boolean verbose = context.has(verboseFlag);
        boolean all = context.has(allFlag);

        if (verbose) {
            // Verbose output
        }
        return CompletableFuture.completedFuture(null);
    }
}
```
