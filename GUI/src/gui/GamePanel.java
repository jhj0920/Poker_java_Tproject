package gui;

import javax.swing.*;
import java.awt.*;
// Need to refactor GamePanel to organize the layout and components for a Texas Hold'em Poker game GUI.

public class GamePanel extends JPanel {
	public GamePanel() {
		// Set the preferred size of the panel
		setPreferredSize(new Dimension(800, 600));
		// Set the background color
		setBackground(new Color(0, 128, 0)); // Green color for the poker table
		// Set the layout manager
		setLayout(new BorderLayout());
		
		// Create a panel for player 2
		JPanel player2Cards = new JPanel();
		add(player2Cards, BorderLayout.NORTH);
		player2Cards.setBackground(getBackground());
		
		// Create a container for the player 2
		JPanel p2CardsGroup = new JPanel();
		p2CardsGroup.setBackground(getBackground());
		player2Cards.add(p2CardsGroup);
		
		Card player2Card1 = new Card("card_back"); // Example card, replace with actual game logic
		p2CardsGroup.add(player2Card1.getCardLabel());
		Card player2Card2 = new Card("card_back"); // Example card, replace with actual game logic
		p2CardsGroup.add(player2Card2.getCardLabel());
		
		// Create a container for the center area
		JPanel centerContainer = new JPanel();
		centerContainer.setLayout(new GridLayout(2, 3)); // Two columns for player areas
		add(centerContainer, FlowLayout.CENTER);
		
		// Create a spacer panel to add a gap between the title and the center
		JPanel spacerPanel = new JPanel();
		spacerPanel.setOpaque(false); // Make it transparent
		centerContainer.add(spacerPanel, BorderLayout.CENTER);
		
		// Create a panel for player 1
		JPanel player1Cards = new JPanel();
		player1Cards.setPreferredSize(new Dimension(170, 0)); // Set fixed width
		player1Cards.setBackground(getBackground());
		add(player1Cards, BorderLayout.WEST);
		
		// Create a container for the player 1
		JPanel p1CardsGroup = new JPanel();
		p1CardsGroup.setBackground(getBackground());
		player1Cards.add(p1CardsGroup);
		
		Card player1Card1 = new Card("card_back"); // Example card, replace with actual game logic
		p1CardsGroup.add(player1Card1.getCardLabel());
		Card player1Card2 = new Card("card_back"); // Example card, replace with actual game logic
		p1CardsGroup.add(player1Card2.getCardLabel());
		
		// Create a panel for the river cards
		JPanel riverCardsPanel = new JPanel();
		centerContainer.add(riverCardsPanel);
		centerContainer.setBackground(getBackground());
		riverCardsPanel.setBackground(getBackground());
		
		// Import images for river cards
		JPanel riverCardsGroup = new JPanel();
		riverCardsGroup.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0)); // No gaps between cards
		riverCardsPanel.add(riverCardsGroup);
		riverCardsGroup.setBackground(getBackground());
		JLabel riverCard1 = new JLabel(CardImageLoader.getCard("card_back"));
		riverCardsGroup.add(riverCard1);
		JLabel riverCard2 = new JLabel(CardImageLoader.getCard("card_back"));
		riverCardsGroup.add(riverCard2);
		JLabel riverCard3 = new JLabel(CardImageLoader.getCard("card_back"));
		riverCardsGroup.add(riverCard3);
		JLabel riverCard4 = new JLabel(CardImageLoader.getCard("card_back"));
		riverCardsGroup.add(riverCard4);
		JLabel riverCard5 = new JLabel(CardImageLoader.getCard("card_back"));
		riverCardsGroup.add(riverCard5);
		
		// Create a panel for player 3
		JPanel player3Cards = new JPanel();
		player3Cards.setPreferredSize(new Dimension(170, 0)); // Set fixed width
		player3Cards.setBackground(getBackground());
		add(player3Cards, BorderLayout.EAST);
		
		// Create a container for the player 3
		JPanel p3CardsGroup = new JPanel();
		p3CardsGroup.setBackground(getBackground());
		player3Cards.add(p3CardsGroup);
		
		Card player3Card1 = new Card("card_back"); // Example card, replace with actual game logic
		p3CardsGroup.add(player3Card1.getCardLabel());
		Card player3Card2 = new Card("card_back"); // Example card, replace with actual game logic
		p3CardsGroup.add(player3Card2.getCardLabel());
		
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
		Card playerCard1 = new Card("ace_of_spades"); // Example card, replace with actual game logic);
		PCardsGroup.add(playerCard1.getCardLabel());
		Card playerCard2 = new Card("ace_of_hearts"); // Example card, replace with actual game logic
		PCardsGroup.add(playerCard2.getCardLabel());
		
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
