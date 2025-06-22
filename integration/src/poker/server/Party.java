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
    private static final int MAX_PLAYERS = 4; // Maximum number of players in a party

    public Party(String partyId, PartyManager partyManager) {
        this.partyId = partyId;
		this.partyManager = partyManager;
    }

    // Synchronized method to add a player to the party
    public synchronized boolean addPlayer(ClientHandler player) {
        if (players.size() < MAX_PLAYERS) {
            players.add(player);
            return true;
        }
        return false;
    }
    
    /**
	 * removePlayer removes a player from the party.
	 * If the party becomes empty after removing the player, it removes the party from the manager.
	 *
	 * @param player The player to be removed from the party.
	 */
    public synchronized void removePlayer(ClientHandler player) {
		players.remove(player);
		// If the party is empty after removing the player, remove the party from the manager
		if (players.isEmpty()) {
			partyManager.removeParty(partyId);
		}
	}

    public boolean isFull() {
        return players.size() == MAX_PLAYERS;
    }

    public String getPartyId() {
        return partyId;
    }
}
