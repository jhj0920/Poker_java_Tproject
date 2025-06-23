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
    private int dealerIndex;
    private int smallBlindIndex;
    private int bigBlindIndex;
    private static final int SMALL_BLIND = 50;
    private static final int BIG_BLIND = 100;
    private final Random random = new Random();

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
        dealerIndex = random.nextInt(handlers.size());
        gameManager.startNewRound();
        setupBlinds();
    }

    /** Handle an action command from a client. */
    public synchronized void handleAction(ClientHandler sender, String action, int amount) {
        Player p = playerMap.get(sender);
        if (p == null) {
            return;
        }
        if (handlers.get(turnIndex) != sender) {
            sender.sendMessage("ERROR 아직 차례가 아닙니다.");
            return;
        }
        String error = null;
        int broadcastAmount = amount;
        switch (action) {
            case "CALL" -> error = bettingManager.call(p);
            case "FOLD" -> error = bettingManager.fold(p);
            case "RAISE" -> error = bettingManager.raise(p, amount);
            case "BET" -> error = bettingManager.raise(p, bettingManager.getCurrentBet() + amount);
            case "ALL_IN" -> {
                broadcastAmount = p.getChips();
                error = bettingManager.allIn(p);
            }
        }
        if (error != null) {
            sender.sendMessage("ERROR " + error);
        } else {
            broadcast("PLAYER_ACTION " + sender.getPlayer().getName() + " " + action + (action.equals("CALL")||action.equals("FOLD")?"":" " + broadcastAmount));
            broadcastBets();
            if (!checkProgress()) {
                nextTurn();
            }
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
    
    private void setupBlinds() {
        if (handlers.size() == 2) {
            // Dealer posts the small blind in heads-up play
            smallBlindIndex = dealerIndex;
            bigBlindIndex = (dealerIndex + 1) % handlers.size();
            // Big blind acts first preflop
            turnIndex = bigBlindIndex;
        } else {
            smallBlindIndex = (dealerIndex + 1) % handlers.size();
            bigBlindIndex = (dealerIndex + 2) % handlers.size();
            // Action begins with the player after the big blind
            turnIndex = (bigBlindIndex + 1) % handlers.size();
        }

        Player sbPlayer = handlers.get(smallBlindIndex).getPlayer();
        Player bbPlayer = handlers.get(bigBlindIndex).getPlayer();
        String err = bettingManager.raise(sbPlayer, SMALL_BLIND);
        if (err != null) {
            bettingManager.allIn(sbPlayer);
        }
        broadcast("PLAYER_ACTION " + sbPlayer.getName() + " BET " + sbPlayer.getCurrentBet());

        err = bettingManager.raise(bbPlayer, BIG_BLIND);
        if (err != null) {
            bettingManager.allIn(bbPlayer);
        }
        broadcast("PLAYER_ACTION " + bbPlayer.getName() + " BET " + bbPlayer.getCurrentBet());
    }
    
    /**
     * Resets {@code turnIndex} to the first active player for a new betting
     * phase (flop and later). The first player after the dealer acts first,
     * or the dealer in heads-up play.
     */
    private void resetTurnForNextPhase() {
        int index = handlers.size() == 2
                ? dealerIndex
                : (dealerIndex + 1) % handlers.size();
        for (int i = 0; i < handlers.size(); i++) {
            Player p = handlers.get(index).getPlayer();
            if (!p.isFolded() && p.getChips() > 0) {
                turnIndex = index;
                break;
            }
            index = (index + 1) % handlers.size();
        }
        broadcastTurn();
    }

    private void nextTurn() {
        for (int i = 0; i < handlers.size(); i++) {
            turnIndex = (turnIndex + 1) % handlers.size();
            if (!handlers.get(turnIndex).getPlayer().isFolded()) {
                break;
            }
        }
        broadcastTurn();
    }
    
    private boolean checkProgress() {
        if (bettingManager.isOnlyOnePlayerRemaining()) {
            Player winner = null;
            for (Player pl : gameManager.getPlayers()) {
                if (!pl.isFolded()) { winner = pl; break; }
            }
            if (winner != null) {
                int total = gameManager.getPot().getTotal();
                winner.setChips(winner.getChips() + total);
                broadcast("WINNER " + winner.getName() + " wins $" + total);
            }
            startNewRound();
            return true;
        }

        if (bettingManager.isRoundComplete()) {
            gameManager.nextPhase();
            for (Player pl : gameManager.getPlayers()) {
                pl.setCurrentBet(0);
            }
            bettingManager.reset();
            if (gameManager.getState() == GameState.SHOWDOWN) {
                String result = HandEvaluator.determineWinner(gameManager.getPlayers(),
                        gameManager.getCommunityCards(), gameManager.getPot());
                broadcast("WINNER " + result.replace('\n', ' '));
                startNewRound();
            } else {
                broadcastCommunity();
                broadcastBets();
                resetTurnForNextPhase();
            }
            return true;
        }
        return false;
    }

    private void startNewRound() {
        dealerIndex = (dealerIndex + 1) % handlers.size();
        gameManager.startNewRound();
        bettingManager.reset();
        setupBlinds();
        broadcastInitialState();
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