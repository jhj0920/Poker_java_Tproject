package poker.server;

import java.util.*;
import poker.logic.*;

/**
 * GameSession hosts a server-side poker game for a Party.
 * It wraps GameManager and BettingRoundManager and updates state
 * according to player actions received from clients.
 */
public class GameSession {
    private final GameManager gameManager;
    private final BettingRoundManager bettingManager;
    private final Map<ClientHandler, Player> playerMap = new HashMap<>();

    public GameSession(List<ClientHandler> handlers) {
        List<Player> players = new ArrayList<>();
        for (ClientHandler h : handlers) {
            Player p = h.getPlayer();
            players.add(p);
            playerMap.put(h, p);
        }
        gameManager = new GameManager(players);
        bettingManager = new BettingRoundManager(gameManager);
        gameManager.startNewRound();
    }

    /** Handle an action command from a client. */
    public synchronized void handleAction(ClientHandler sender, String action, int amount) {
        Player p = playerMap.get(sender);
        if (p == null) {
            return;
        }
        String error = null;
        switch (action) {
            case "CALL" -> error = bettingManager.call(p);
            case "FOLD" -> error = bettingManager.fold(p);
            case "RAISE" -> error = bettingManager.raise(p, amount);
            case "BET" -> error = bettingManager.raise(p, bettingManager.getCurrentBet() + amount);
        }
        if (error != null) {
            sender.sendMessage("ERROR " + error);
        } else {
            broadcast(sender.getPlayer().getName() + " " + action + (action.equals("CALL")||action.equals("FOLD")?"":" " + amount));
        }
    }

    private void broadcast(String msg) {
        for (ClientHandler h : playerMap.keySet()) {
            h.sendMessage("GAME " + msg);
        }
    }
}