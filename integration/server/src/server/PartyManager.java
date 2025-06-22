package server;
import java.util.*;
import java.util.concurrent.*;
// handles party management in the matchmaking server
// parties may become full, so we need to manage them
// Changed to allow parties to not be removed immediately when empty, but rather when the last player leaves

public class PartyManager {
	// Map to store parties by their ID
    private final Map<String, Party> parties = new ConcurrentHashMap<>();
    // Random number generator for creating unique party IDs
    private final Random random = new Random();

    public String createParty() {
        String partyId;
        do {
            partyId = String.format("%04d", random.nextInt(10000));  // 4-digit room ID
        } while (parties.containsKey(partyId)); // containsKey checks if the party ID already exists
        // If the party ID is unique, create a new Party object and add it to the map
        parties.put(partyId, new Party(partyId, this)); // Pass the PartyManager instance to the Party constructor
        return partyId;
    }

    public Party getParty(String partyId) {
        return parties.get(partyId);
    }

    public void removeParty(String partyId) {
        parties.remove(partyId);
    }
}
