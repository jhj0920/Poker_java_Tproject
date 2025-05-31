package server;
import java.util.*;

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
    
    // Synchronized method to remove a player from the party
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
