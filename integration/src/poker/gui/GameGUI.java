package poker.gui;
import javax.swing.*;
import java.awt.*;

/**
 * Main class to create and display the game GUI.
 * This class initializes the main frame and adds the GamePanel to it.
 */
public class GameGUI {
	public static void main(String[] args) {
		// Create the main frame
		JFrame frame = new JFrame("Game GUI");
		// add components to the frame
		JPanel panel = new GamePanel();
		
		frame.getContentPane().add(panel);
		frame.setTitle("Texas Hold'em Poker"); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 750);
		
		// Set the frame to be visible
		frame.setVisible(true);
		
	}
}
