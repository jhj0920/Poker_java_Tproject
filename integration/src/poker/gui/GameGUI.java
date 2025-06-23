package poker.gui;

import poker.logic.GameManager;
import poker.logic.Player;
import poker.logic.logicCard;

import javax.swing.*;
import java.util.*;
import java.io.PrintWriter;

/**
 * Main class to create and display the game GUI.
 * This class initializes the main frame and adds the GamePanel to it.
 */
public class GameGUI {
    private final JFrame frame;
    private final GamePanel panel;
    private final GameManager gameManager;
    private final PrintWriter out;

    private final Map<String, Integer> nameToIndex = new HashMap<>();
    private int nextIndex = 0;
    private String myName;

    public GameGUI(PrintWriter out) {
        this.out = out;
        this.gameManager = new GameManager(createPlayers());
        this.panel = new GamePanel(gameManager, out);

        frame = new JFrame("Texas Hold'em Poker");
        frame.add(panel);
        frame.setSize(1000, 750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void processServerMessage(String line) {
        if (!line.startsWith("GAME")) {
            return;
        }

        String rest = line.substring(5); // remove "GAME "
        String[] parts = rest.split(" ", 4);
        switch (parts[0]) {
            case "HAND" -> handleHand(parts[1], parts.length > 2 ? parts[2] : "");
            case "COMMUNITY" -> handleCommunity(parts.length > 1 ? parts[1] : "");
            case "BET" -> {
                if (parts.length >= 4) {
                    handleBet(parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
                }
            }
            case "TURN" -> {
                if (parts.length >= 2) {
                    handleTurn(parts[1]);
                }
            }
            case "PLAYER_ACTION" -> {
                // could display in chat panel
            }
        }
        SwingUtilities.invokeLater(panel::refreshAll);
    }

    private void handleHand(String player, String cards) {
        myName = player; // each client only receives their own hand
        Player p = getPlayer(player);
        p.getHand().clear();
        for (logicCard c : parseCards(cards)) {
            p.getHand().addCard(c);
        }
    }

    private void handleCommunity(String cards) {
        List<logicCard> list = parseCards(cards);
        gameManager.getCommunityCards().clear();
        gameManager.getCommunityCards().addAll(list);
        if (list.isEmpty()) {
            panel.hideOpponents();
        }
    }

    private void handleBet(String player, int bet, int chips) {
        Player p = getPlayer(player);
        p.setCurrentBet(bet);
        p.setChips(chips);
    }

    private void handleTurn(String player) {
        Integer idx = nameToIndex.get(player);
        if (idx != null) {
            panel.highlightTurn(idx);
        }
    }


    private Player getPlayer(String name) {
        Integer idx = nameToIndex.get(name);
        if (idx == null) {
            if (name.equals(myName)) {
                idx = 3; // you are always bottom player
            } else {
                idx = nextIndex++;
                if (idx >= 3) idx = 2; // clamp
            }
            nameToIndex.put(name, idx);
        }
        return gameManager.getPlayers().get(idx);
    }

    private List<Player> createPlayers() {
        return Arrays.asList(
                new Player("P1", 0),
                new Player("P2", 0),
                new Player("P3", 0),
                new Player("You", 0)
        );
    }
    
    private List<logicCard> parseCards(String str) {
        List<logicCard> list = new ArrayList<>();
        if (str == null || str.isEmpty()) return list;
        for (String c : str.split(",")) {
            String[] p = c.split("_");
            try {
                logicCard.Rank r = logicCard.Rank.valueOf(p[0]);
                logicCard.Suit s = logicCard.Suit.valueOf(p[1]);
                list.add(new logicCard(s, r));
            } catch (IllegalArgumentException e) {
                // ignore malformed card
            }
        }
        return list;
    }
}
