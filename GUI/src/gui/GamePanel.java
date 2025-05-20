package gui;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
	public GamePanel() {
		// Set the preferred size of the panel
		setPreferredSize(new Dimension(800, 600));
		// Set the background color
		setBackground(new Color(0, 128, 0)); // Green color for the poker table
		// Set the layout manager
		setLayout(new BorderLayout());
		
		// Create a label for the title
		JLabel titleLabel = new JLabel("Texas Hold'em Poker", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
		add(titleLabel, BorderLayout.NORTH);
		
		 // Create a container for the south border
        JPanel southContainer = new JPanel();
        southContainer.setLayout(new BorderLayout());

        // Create a panel for player cards
        JPanel playerCardsPanel = new JPanel();
        playerCardsPanel.setLayout(new GridLayout(1, 2, 0, 0)); // No gaps between cards
        southContainer.add(playerCardsPanel, BorderLayout.CENTER);
        
        // Create another panel to group the cards together and be right next to each other
        JPanel PCardsGroup = new JPanel();
        playerCardsPanel.add(PCardsGroup);
		
		// Import images for player cards
		JLabel playerCard1 = new JLabel(CardImageLoader.getCard("card_back"));
		PCardsGroup.add(playerCard1);
		JLabel playerCard2 = new JLabel(CardImageLoader.getCard("card_back"));
		PCardsGroup.add(playerCard2);
		
		// Create a button to start the game
		JButton startButton = new JButton("Start Game");
		southContainer.add(startButton, BorderLayout.SOUTH);
		
		 // Add the south container to the south border
        add(southContainer, BorderLayout.SOUTH);
        southContainer.setBackground(getBackground());
        PCardsGroup.setBackground(getBackground());

		
		startButton.addActionListener(e -> {
			// Add game logic here
		});
		
	}
}
