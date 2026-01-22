# Command Permissions

The command system includes a robust permission system to control who can execute commands.

## Automatic Permission Generation

By default, commands auto-generate permission nodes based on the plugin’s group, name, and the command's name.

*   **Plugin group**: `com.example`
*   **Plugin name**: `MyPlugin`
*   **Command**: `mycommand`

**Generated permission**: `com.example.myplugin.command.mycommand`

For subcommands, the permissions are chained:
`com.example.myplugin.command.admin.kick`

## Custom Permission Node

You can override the default behavior and specify a completely custom permission node by overriding `generatePermissionNode()`.

```java
public class ProtectedCommand extends AbstractCommand {

    public ProtectedCommand() {
        super("protected", "A protected command");
    }

    @Override @Nullable
    protected String generatePermissionNode() {
        return "custom.permission.node";
    }

    @Override @Nonnull
    protected CompletableFuture<Void> execute( @Nonnull CommandContext context) {
        // Only executed if sender has "custom.permission.node"
        return CompletableFuture.completedFuture(null);
    }
}
```

## Disable Permission Check

To make a command usable by anyone, regardless of permissions, override `canGeneratePermission()` and return `false`.

```java
@Override
protected boolean canGeneratePermission() {
    return false;  // Anyone can use this command
}
```

## Manual Permission Check

You can perform manual permission checks within the `execute` method for more granular control.

```java
@Override @Nonnull
protected CompletableFuture<Void> execute( @Nonnull CommandContext context) {
    if (!context.sender().hasPermission("special.action")) {
        context.sender().sendMessage(Message.raw("You don't have permission for this special action!"));
        return CompletableFuture.completedFuture(null);
    }
    // Continue with action...
    return CompletableFuture.completedFuture(null);
}
```

## Require Specific Permission

You can also set a required permission directly in the constructor. This is cleaner than overriding `generatePermissionNode()` if you just need to set a simple node.

```java
public class SecureCommand extends AbstractCommand {

    public SecureCommand() {
        super("secure", "A secure command");
        requirePermission("my.custom.permission"); // Explicitly require this permission
    }
}
```
