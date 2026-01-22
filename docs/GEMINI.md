# Hytale Server Documentation

This document provides a high-level overview of the Hytale Server project structure, which is a Java-based server for the game Hytale.

## Project Overview

The project is a Hytale server written in Java, using Maven for dependency management. The source code is organized into a modular structure, with a core engine and various plugins for different game features.

### Key Technologies

*   **Java**: The primary programming language.
*   **Maven**: Used for dependency management and building the project.
*   **fastutil**: A library for efficient primitive type collections.

## Development Conventions

The project follows standard Java coding conventions. The codebase is organized into a hierarchical package structure, with each package responsible for a specific domain of the game server. The project heavily utilizes an Entity-Component System (ECS) architecture.

## Key Packages

*   `com.hypixel.hytale.server`: The core server implementation.
*   `com.hypixel.hytale.component`: The Entity-Component System (ECS) framework.
*   `com.hypixel.hytale.plugin`: The plugin framework for extending server functionality.
*   `com.hypixel.hytale.protocol`: The network protocol for client-server communication.
*   `com.hypixel.hytale.builtin`: Built-in game features, such as adventure mode, crafting, and weather.

## ZoneUI - Modular Hytale UI Backend

The `ZoneUI` project provides a modular and simplified API for creating interactive UI pages in the Hytale server. It abstracts away much of the boilerplate code required by Hytale's native `InteractiveCustomUIPage` system, allowing for quicker development of UI elements.

### Key Components of ZoneUI API

*   **`UIManager`**: The central class for managing UI pages. It allows for registering, showing, and closing UI pages for players.
*   **`UIPage`**: Represents a single UI page defined by a `.ui` file. It facilitates binding server-side actions to client-side UI events (e.g., button clicks).
*   **`UIAction`**: A functional interface defining the server-side logic to execute when a UI event is triggered.
*   **`UIInitializer`**: A dedicated class to encapsulate the setup and registration of all UI pages and commands within the plugin. This keeps the main `ZoneUI` plugin class clean.

### Simplified UI Development Workflow

The `ZoneUI` API simplifies UI development as follows:

1.  **Define UI Layout**: Create `.ui` files in `src/main/resources/Common/UI/Custom/Pages/`.
2.  **Initialize `UIManager`**: In your plugin's `setup()` method, create an instance of `UIManager`.
3.  **Create `UIPage`**: Instantiate a `UIPage` with the path to your `.ui` file.
4.  **Bind Events**: Use `UIPage.onButtonClick()` (or similar methods for other events) to define server-side actions using lambda expressions. These actions receive data directly from UI components (e.g., text input values).
5.  **Register Page**: Register the `UIPage` with the `UIManager` using a unique ID.
6.  **Show Page**: Use `uiManager.showPage()` from your commands or other server logic to display the page to a player.

### Example Usage (from `UIInitializer`)

```java
// --- Welcome Page Setup ---
UIPage welcomePage = new UIPage("Pages/WelcomePage.ui")
        .onButtonClick("#GreetButton", (player, ref, store, data) -> {
            // Access data from UI component (e.g., input field with " @PlayerName" key)
            String playerName = data.playerName != null && !data.playerName.isEmpty() ? data.playerName : "Stranger";
            player.sendMessage(Message.raw("Hello, " + playerName + "!"));
            uiManager.closePage(player, ref, store);
        });
uiManager.registerPage("welcome", welcomePage);
```

This `GEMINI.md` file provides a starting point for understanding the project. For more detailed information, please refer to the `GEMINI_full_summary.md` file.
