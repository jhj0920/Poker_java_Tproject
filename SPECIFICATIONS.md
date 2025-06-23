# Poker Project Specifications

This document summarizes the architecture of the poker project and highlights key design patterns used throughout the codebase.

## Overall Architecture

The project is divided into three main packages:

- `poker.gui` – Contains Swing based GUI components and the `PokerLauncher` entry point.
- `poker.logic` – Core gameplay logic including players, cards, deck, hand evaluation and betting management.
- `poker.server` – Networking layer that allows multiple players to connect and play together.

The GUI interacts with the logic package and sends commands to the server. The server maintains the authoritative game state using the same logic classes.

## Key Classes and Responsibilities

### `GameManager`
Coordinates the overall flow of a game round. It deals cards, advances game phases (preflop, flop, turn, river), and manages the dealer position.

### `BettingRoundManager`
Encapsulates betting logic for a single round. It tracks the current bet, validates actions (call, raise, check, fold, all-in), and updates the `Pot`. A callback can be supplied so the GUI refreshes when betting state changes.

### `Pot`
Tracks chips wagered by each player. It automatically separates the main and side pots when an all-in occurs.

### `HandEvaluator`
Provides static methods for ranking poker hands and determining the winner once all cards are revealed.

### `GameSession`
Server-side controller that connects `GameManager` and `BettingRoundManager`. It processes actions from clients, broadcasts state updates, and starts new rounds when appropriate.

## Design Patterns

- **Model–View–Controller (MVC)**: The logic classes act as the model, the Swing classes form the view, and `GameSession` or `PokerLauncher` serve as controllers.
- **Observer**: `BettingRoundManager` notifies `BettingObserver` instances whenever betting data changes.
- **Command**: `PlayerAction` enum encapsulates logic for each server action instead of a switch statement.
- **Singleton**: `CardImageLoader` loads card images once and provides global access via `getInstance()`.
- **Factory Method**: `GameGUI` uses `GridBagConstraintsFactory` to create layout constraints in a reusable manner.

## Potential Improvements

1. **Interface Driven Design** – Introduce interfaces for components like `GameView` or `NetworkClient` to decouple GUI from networking.
2. **Package by Feature** – Split packages further into smaller, feature-oriented subpackages.
3. **Unit Tests** – Add more unit tests beyond `PotTest` to cover betting, hand evaluation and server logic.
4. **Build System** – Adopt Maven or Gradle to manage dependencies such as JUnit.

