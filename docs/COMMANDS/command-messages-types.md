# Messages & Argument Types

This section covers how to send feedback messages to the command sender and the available argument types.

## Messages

The `Message` class provides a way to send formatted text to players, including support for translations.

### Raw Message

Sends a simple, un-translated string.

```java
context.sender().sendMessage(Message.raw("Hello World"));
```

### Translation Message

Sends a message that uses a translation key from the game's language files. This allows the message to be displayed in the player's configured language. You can also provide parameters for substitution.

```java
// Uses translation key "server.commands.give.success" with parameters
context.sender().sendMessage(
    Message.translation("server.commands.give.success")
        .param("player", playerName)
        .param("amount", amount)
);
```

## Argument Types

The `ArgTypes` class provides static instances for common argument types. For more complex types, you can instantiate them directly (e.g., `IntArgumentType.ranged(min, max)`).

| Type                  | Description                                            |
| --------------------- | ------------------------------------------------------ |
| `StringArgumentType`  | A text string.                                         |
| `IntArgumentType`     | An integer, with optional min/max via `ranged(min, max)`. |
| `DoubleArgumentType`  | A double, with optional min/max.                       |
| `BooleanArgumentType` | A boolean value (`true` or `false`).                   |
| `ListArgumentType<T>` | A list of values (e.g., for targeting multiple players).|
