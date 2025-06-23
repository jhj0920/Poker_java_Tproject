package poker.server;
import java.util.*;

/**
 * Party class represents a group of players in the matchmaking system.
 * It manages the players in the party and provides methods to add or remove players.
 */
public class Party {
    private final String partyId;
    private final PartyManager partyManager;
    // List to hold players in the party
    private final List<ClientHandler> players = new ArrayList<>();
    // The first player to create the party becomes the leader
    private ClientHandler leader;
    private GameSession gameSession;
    private static final int MAX_PLAYERS = 4; // Maximum number of players in a party

    public Party(String partyId, PartyManager partyManager) {
        this.partyId = partyId;
		this.partyManager = partyManager;
    }

    // Synchronized method to add a player to the party
    public synchronized boolean addPlayer(ClientHandler player) {
        if (players.contains(player)) {
            return false; // already in party
        }
        if (players.size() < MAX_PLAYERS) {
            players.add(player);
            if (players.size() == 1) {
                leader = player; // first player becomes leader
            }
            broadcast("PLAYER_COUNT " + players.size());
            if (isFull()) {
                startGame();
            }
            return true;
        }
        return false;
    }
    
    // Check if a player is already in the party
    public synchronized boolean hasPlayer(ClientHandler player) {
        return players.contains(player);
    }
    
    /**
	 * removePlayer removes a player from the party.
	 * If the party becomes empty after removing the player, it removes the party from the manager.
	 *
	 * @param player The player to be removed from the party.
	 */
    public synchronized void removePlayer(ClientHandler player) {
        players.remove(player);
        if (players.isEmpty()) {
            partyManager.removeParty(partyId);
        } else {
            broadcast("PLAYER_COUNT " + players.size());
        }
    }

    public boolean isFull() {
        return players.size() == MAX_PLAYERS;
    }
    
    public int getPlayerCount() {
        return players.size();
    }

    public ClientHandler getLeader() {
        return leader;
    }

    public void broadcast(String message) {
        for (ClientHandler p : players) {
            p.sendMessage(message);
        }
    }
    
    private void startGame() {
        gameSession = new GameSession(players);
        broadcast("GAME_START");
    }

    public GameSession getGameSession() {
        return gameSession;
    }

    public String getPartyId() {
        return partyId;
    }
}
