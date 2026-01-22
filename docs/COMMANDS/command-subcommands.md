# Subcommands

For better organization, you can group related commands together as subcommands under a single parent command. `AbstractCommandCollection` is used for this purpose.

## Command Collection Example

Create a main class that extends `AbstractCommandCollection` and add your subcommands to it.

```java
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.StringArgumentType;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

public class AdminCommands extends AbstractCommandCollection {

    public AdminCommands() {
        super("admin", "Admin commands");

        // Add subcommands
        addSubCommand(new KickSubCommand());
        addSubCommand(new BanSubCommand());
        addSubCommand(new MuteSubCommand());
    }
}

// Example subcommand
class KickSubCommand extends AbstractAsyncCommand {

    private final RequiredArg<String> playerArg;

    public KickSubCommand() {
        super("kick", "Kick a player");
        playerArg = withRequiredArg("player", "Player to kick", StringArgumentType.INSTANCE);
    }

    @Override @Nonnull
    protected CompletableFuture<Void> executeAsync( @Nonnull CommandContext context) {
        String player = context.get(playerArg);
        // Kick implementation...
        System.out.println("Kicking player: " + player);
        return CompletableFuture.completedFuture(null);
    }
}

// Other subcommands (Ban, Mute) would be defined similarly.
class BanSubCommand extends AbstractAsyncCommand {
    public BanSubCommand() { super("ban", "Ban a player"); }
    // ...
}

class MuteSubCommand extends AbstractAsyncCommand {
    public MuteSubCommand() { super("mute", "Mute a player"); }
    // ...
}
```

### Usage

Subcommands are executed by chaining their names after the parent command.

`/admin kick PlayerName`

`/admin ban AnotherPlayer`
