package poker.server;
import java.io.*;
import java.net.*;
// Handles client connections and matchmaking in the server for each client

/** * ClientHandler class manages the connection with a single client.
 * It allows clients to create or join parties and handles communication.
 */
public class ClientHandler implements Runnable {
    private final Socket socket;
    private final PartyManager partyManager;
    // BufferedReader and PrintWriter for reading from and writing to the client
    private PrintWriter out;
    private BufferedReader in;
    private Party currentParty;

    public ClientHandler(Socket socket, PartyManager partyManager) {
        this.socket = socket;
        this.partyManager = partyManager;
    }

    @Override
    public void run() {
        try {
            in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Welcome! Enter command: CREATE or JOIN <partyID>");

            while (true) {
            	// Read input from the client
                String input = in.readLine();
                if (input == null) break;
                String[] tokens = input.trim().split(" "); // tokens is an array of strings split by spaces
                String command = tokens[0].toUpperCase();

                // Create a new party
                if (command.equals("CREATE")) {
                    String partyId = partyManager.createParty();
                    if (partyId == null) {
                        out.println("ERROR Server full.");
                    } else {
                        currentParty = partyManager.getParty(partyId);
                        currentParty.addPlayer(this); // Add the player to the newly created party
                        out.println("PARTY_CREATED " + partyId);
                        System.out.println("Party " + partyId + " created.");
                    }
                // Join an existing party
                } else if (command.equals("JOIN") && tokens.length == 2) {
                    String partyId = tokens[1]; // Get the party ID from the second token
                    Party party = partyManager.getParty(partyId);
                    if (party == null) {
                        out.println("ERROR Party not found.");
                    } else if (party.hasPlayer(this)) {
                        out.println("ERROR Already in party.");
                    } else if (party.addPlayer(this)) {
                        currentParty = party;
                        out.println("JOINED " + partyId);
                        if (party.isFull()) {
                            out.println("PARTY_READY");
                            System.out.println("Party " + partyId + " is now full.");
                        }
                    } else {
                        out.println("ERROR Party is full.");
                    }
                } else if (command.equals("START")) {
                    if (currentParty == null) {
                        out.println("ERROR Not in a party.");
                    } else if (currentParty.getLeader() != this) {
                        out.println("ERROR Only leader can start.");
                    } else if (currentParty.getPlayerCount() <= 2) {
                        out.println("ERROR Not enough players.");
                    } else {
                        currentParty.broadcast("GAME_START");
                        System.out.println("Party " + currentParty.getPartyId() + " starting game.");
                        partyManager.removeParty(currentParty.getPartyId());
                    }
                } else {
                    out.println("ERROR Invalid command.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        // finally block ensures that resources are cleaned up
        } finally {
            cleanup();
        }
    }

    /**
     * Sends a message to the client.
     */
    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }

    /**
     * Cleanup resources when the client disconnects.
     */
    private void cleanup() {
        try {
        	// Close the input and output streams
            if (currentParty != null) {
                System.out.println("Client disconnected from party " + currentParty.getPartyId());
                // You can implement party cleanup here if desired
                currentParty.removePlayer(this);
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
