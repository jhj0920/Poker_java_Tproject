# Poker Networking Protocol

This document defines the text based messages exchanged between the server and clients.  All messages are newline separated and consist of space delimited tokens.

## Connection Flow
1. Client connects via TCP and receives a greeting: `Welcome! Enter command: CREATE or JOIN <partyID>`.
2. The client issues `CREATE` to start a new party or `JOIN <id>` to enter an existing one.
3. Server responses include:
   - `PARTY_CREATED <id>` – a new party was created and the client becomes the leader.
   - `JOINED <id>` – joined an existing party.
   - `PLAYER_COUNT <n>` – broadcast whenever the number of players in the party changes.
   - `PARTY_READY` – sent when the party reaches the maximum size (four players).
   - `GAME_START` – the game session begins and the first state messages are sent.
   - Any line starting with `ERROR` indicates a problem with the issued command.

## Lobby Commands
Clients may issue these commands while in the lobby:

* `CREATE` – Create a new party.
* `JOIN <partyId>` – Join an existing party.
* `START` – Start the game when issued by the party leader.
* `CHAT <message>` – Send a chat message to the current party.
* `LEAVE` – Leave the current party. The server replies with `LEFT`.

## Game Commands
During a game each line represents a single action:

* `BET <amount>` – Place an opening bet.
* `CALL` – Match the current bet.
* `RAISE <amount>` – Increase the bet to the given total.
* `CHECK` – Take no betting action when no call is required.
* `FOLD` – Discard the hand and forfeit the pot.
* `ALL_IN` – Bet all remaining chips.

## Game Events
Messages beginning with `GAME` are broadcast from the server to update clients:

* `HAND <player> <card1>,<card2>` – two cards dealt to a player.
* `COMMUNITY <card1>,<card2>,...` – shared community cards on the table.
* `BET <player> <bet> <chips>` – total bet and remaining chips for a player.
* `TURN <player>` – indicates who must act next.
* `PLAYER_ACTION <player> <ACTION> [amount]` – action taken by a player.
* `WINNER <summary>` – description of the winning hand at showdown.
* `VICTORY <player>` – a player has bankrupted all others and wins the game.
* `CHAT <player>: <message>` – chat message delivered to everyone in the game.

Clients should parse these messages and update their UI accordingly.
