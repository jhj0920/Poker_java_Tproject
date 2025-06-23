# Poker Project

A multiplayer poker game implemented in Java to practice **object-oriented programming** and the application of common **design patterns**.  The codebase contains a lightweight matchmaking server, a Swing based client GUI and all core game logic.

## Overview
- Players connect to a matchmaking lobby where parties of up to four people are created.
- Once a party is full the server starts a game session and the graphical table is displayed.
- The entire application is written in Java 17 and communicates over a simple text protocol described in [PROTOCOL.md](PROTOCOL.md).

## Directory Structure
- `poker.gui` – Swing GUI including the `PokerLauncher` entry point.
- `poker.logic` – Game mechanics such as the deck, hand evaluation and betting utilities.
- `poker.server` – Networking layer that keeps the authoritative state and relays messages.

See [SPECIFICATIONS.md](SPECIFICATIONS.md) for a deeper discussion of the architecture and class responsibilities.

## Running the Lobby
1. Compile all sources:
   ```bash
   javac $(find integration/src -name '*.java')
   ```
2. Launch the lobby and server together (host mode):
   ```bash
   java -cp integration/src poker.gui.PokerLauncher
   ```

   Use `--client-only` with optional `--host` and `--port` to connect to a remote server:
   ```bash
   java -cp integration/src poker.gui.PokerLauncher --client-only --host 192.168.1.10 --port 8888
   ```

   When `--client-only` is omitted, the launcher starts a local server and opens the lobby connected to `localhost:8888`.  With the flag, only the lobby is started and it connects to the specified address.

When enough players join and the leader presses **Start Game**, the game GUI will appear.

## Design Patterns Used
- **Model–View–Controller (MVC)** – GUI classes represent the *view*, logic classes are the *model* and `GameSession`/`PokerLauncher` act as *controllers*.
- **Observer** – `BettingRoundManager` can notify GUI callbacks whenever betting data changes.
- **Command** – Server actions are handled by string commands (`BET`, `CALL`, etc.) rather than large `if`/`switch` blocks.
- **Singleton** – `CardImageLoader` loads card images once and exposes them globally.
- **Factory Method** – `GridBagConstraintsFactory` centralizes creation of layout constraints for consistent GUI layout.

These patterns helped keep the code modular while exploring different object-oriented techniques.
