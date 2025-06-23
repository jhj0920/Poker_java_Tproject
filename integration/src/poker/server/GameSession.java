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
    private final List<ClientHandler> handlers;
    private int turnIndex = 0;

    public GameSession(List<ClientHandler> handlers) {
    	this.handlers = new ArrayList<>(handlers);
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
        	broadcast("PLAYER_ACTION " + sender.getPlayer().getName() + " " + action + (action.equals("CALL")||action.equals("FOLD")?"":" " + amount));
            broadcastBets();
            nextTurn();
        }
    }

    private void broadcast(String msg) {
        for (ClientHandler h : playerMap.keySet()) {
            h.sendMessage("GAME " + msg);
        }
    }
    

    /** Sends initial state to all players when a round begins. */
    public void broadcastInitialState() {
        broadcastHands();
        broadcastCommunity();
        broadcastBets();
        broadcastTurn();
    }

    private void broadcastHands() {
        for (ClientHandler h : handlers) {
            Player p = playerMap.get(h);
            String cards = encodeCards(p.getHand().getCards());
            h.sendMessage("GAME HAND " + p.getName() + " " + cards);
        }
    }

    private void broadcastCommunity() {
        String cards = encodeCards(gameManager.getCommunityCards());
        broadcast("COMMUNITY " + cards);
    }

    private void broadcastBets() {
        for (Player p : gameManager.getPlayers()) {
            broadcast("BET " + p.getName() + " " + p.getCurrentBet() + " " + p.getChips());
        }
    }

    private void broadcastTurn() {
        String name = handlers.get(turnIndex).getPlayer().getName();
        broadcast("TURN " + name);
    }

    private void nextTurn() {
        turnIndex = (turnIndex + 1) % handlers.size();
        broadcastTurn();
    }

    private String encodeCards(List<logicCard> cards) {
        StringBuilder sb = new StringBuilder();
        for (logicCard c : cards) {
            if (sb.length() > 0) sb.append(',');
            sb.append(c.getRank()).append('_').append(c.getSuit());
        }
        return sb.toString();
    }
}