# Built-in Commands & Base Classes

This section lists some of the server's built-in commands and the various abstract base classes you can extend for more specialized command functionality.

## Built-in Commands

The server includes several standard commands for administration and testing.

| Command      | Description                               |
|--------------|-------------------------------------------|
| `help`       | Lists available commands.                 |
| `stop`       | Stops the server.                         |
| `kick <p>`   | Kicks a player.                           |
| `who`        | Lists online players.                     |
| `gamemode <m>`| Changes game mode.                      |
| `give <p><i>`| Gives items to a player.                  |
| `tp <x> <y> <z>`| Teleports to coordinates.             |
| `entity`     | Provides entity management subcommands.   |
| `chunk`      | Provides chunk management subcommands.    |
| `worldgen`   | Provides world generation commands.       |

## Base Command Classes

While `AbstractCommand` is the most common base class, several others are available for more specific use cases.

| Class                         | Description                                            |
| ----------------------------- | ------------------------------------------------------ |
| `AbstractCommand`             | The standard base class for all commands.              |
| `AbstractAsyncCommand`        | For commands that perform long-running or I/O operations, using `executeAsync()`. |
| `AbstractCommandCollection`   | Used to group related subcommands together.          |
| `AbstractPlayerCommand`       | A convenience class that requires the command sender to be a `Player`. |
| `AbstractWorldCommand`        | A base for commands that operate on a specific world.  |
| `AbstractTargetPlayerCommand` | A base for commands that target another player as an argument. |
| `AbstractTargetEntityCommand` | A base for commands that target other entities.        |
