# Advanced Command Features

This section covers additional features of the command system, including aliases, confirmations, and programmatic execution.

## Command Aliases

You can provide alternative names for your command using `addAliases()`.

```java
public class TeleportCommand extends AbstractCommand {

    public TeleportCommand() {
        super("teleport", "Teleport to location");

        // Add multiple aliases at once
        addAliases("tp", "warp", "goto");
    }
}
```
Now, `/teleport`, `/tp`, `/warp`, and `/goto` will all trigger this command.

## Confirmation Required

For potentially dangerous commands, you can require the user to add a `--confirm` flag to execute it. This is done by passing `true` as the third argument to the `AbstractCommand` constructor.

```java
public class ResetCommand extends AbstractCommand {

    public ResetCommand() {
        super("reset", "Reset all data", true);  // true = requires confirmation
    }

    @Override @Nonnull
    protected CompletableFuture<Void> execute( @Nonnull CommandContext context) {
        // This code is only called if the user runs "/reset --confirm"
        resetAllData();
        return CompletableFuture.completedFuture(null);
    }
}
```
**Usage**: `/reset --confirm`

## Running Commands Programmatically

You can execute commands from your plugin code using the `CommandManager`. This is useful for macros, scripted events, or NPCs.

```java
import com.hypixel.hytale.server.core.command.system.CommandManager;

// Get the CommandSender (e.g., a PlayerRef or the ConsoleSender)
CommandSender sender = ...; 

// Execute a single command
// This returns a CompletableFuture<Void> that completes when the command finishes.
CommandManager.get().handleCommand(sender, "give PlayerName stone 64");

// Using a PlayerRef as the sender
CommandManager.get().handleCommand(playerRef, "tp 0 64 0");

// Execute multiple commands in sequence
Deque<String> commands = new ArrayDeque<>();
commands.add("command1");
commands.add("command2");
CommandManager.get().handleCommands(sender, commands);

// Execute a command from the console
CommandManager.get().handleCommand(ConsoleSender.INSTANCE, "stop");
```
