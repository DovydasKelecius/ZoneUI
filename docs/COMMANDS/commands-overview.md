# Command System Overview

The Hytale command system provides a flexible API for creating custom server commands for your plugin. You can define commands with required and optional arguments, subcommands, permissions, and more.

## Architecture
```
CommandManager (Singleton via CommandManager.get())
├── System Commands (server built-ins)
└── Plugin Commands (per-plugin via CommandRegistry)
    └── AbstractCommand
        ├── RequiredArg (positional arguments)
        ├── OptionalArg (--name value)
        ├── DefaultArg (--name value with default)
        ├── FlagArg (--flag boolean switches)
        ├── SubCommands (nested commands via addSubCommand)
        └── UsageVariants (commands with different argument counts via addUsageVariant)
```

## Creating a Simple Command

Commands extend `AbstractCommand` and implement the `execute` method, which receives a `CommandContext`.

```java
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.Message;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class HelloCommand extends AbstractCommand {

    public HelloCommand() {
        super("hello", "Says hello to a player");
    }

    @Override @Nullable
    protected CompletableFuture<Void> execute( @Nonnull CommandContext context) {
        context.sender().sendMessage(
            Message.raw("Hello, " + context.sender().getDisplayName() + "!")
        );
        return CompletableFuture.completedFuture(null);
    }
}
```

### Registering the Command

Register your commands in your plugin’s `setup()` method.
```java
 @Override
protected void setup() {
    getCommandRegistry().registerCommand(new HelloCommand());
}
```

## Command Sender

The `CommandSender` interface provides information about who executed the command.

### CommandSender Interface
```java
public interface CommandSender extends IMessageReceiver, PermissionHolder {
    String getDisplayName();
    UUID getUuid();
}

// From IMessageReceiver:
// void sendMessage( @Nonnull Message message);

// From PermissionHolder:
// boolean hasPermission( @Nonnull String id);
// boolean hasPermission( @Nonnull String id, boolean def);
```

### Checking Sender Type

You can check if the sender is a player or the console. The `Player` component implements `CommandSender`.

```java
 @Override @Nonnull
protected CompletableFuture<Void> execute( @Nonnull CommandContext context) {
    CommandSender sender = context.sender();

    if (sender instanceof Player) {
        Player player = (Player) sender;
        // Player-specific logic
    } else if (sender instanceof ConsoleSender) {
        // Console-specific logic
    }
    return CompletableFuture.completedFuture(null);
}
```

### ConsoleSender

The console sender is a singleton that always has all permissions.

```java
// Singleton instance
ConsoleSender console = ConsoleSender.INSTANCE;
console.sendMessage(Message.raw("Console message"));

// hasPermission always returns true for ConsoleSender
console.hasPermission("any.permission"); // true
```

## Best Practices

*   **Use typed arguments**: Store `RequiredArg`/`OptionalArg` as fields for type safety.
*   **Return `CompletableFuture`**: Commands run asynchronously on worker threads.
*   **Use descriptive help**: Good descriptions help users understand commands.
*   **Validate input**: Check argument values before use.
*   **Handle errors gracefully**: Send clear error messages via `Message`.
*   **Use permissions**: Protect sensitive commands with proper permission nodes.
*   **Use subcommands**: Group related functionality under command collections.
*   **Use `AbstractAsyncCommand`**: For commands that perform I/O or long operations.
