package poker.gui;

import poker.logic.GameManager;
import poker.logic.Player;

import javax.swing.*;
import java.util.List;
import java.util.*;

/**
 * Main class to create and display the game GUI.
 * This class initializes the main frame and adds the GamePanel to it.
 */
public class GameGUI {
	public static void main(String[] args) {
		GameManager gameManager = new GameManager(createPlayers());
		
        JFrame frame = new JFrame("Texas Hold'em Poker");
        frame.add(new GamePanel(gameManager));
        frame.setSize(1000, 750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
		
	}
    private static List<Player> createPlayers() {
        return Arrays.asList(
            new Player("Player 1", 10000),
            new Player("Player 2", 10000),
            new Player("Player 3", 10000),
            new Player("Player 4", 10000)
        );
    }
}
