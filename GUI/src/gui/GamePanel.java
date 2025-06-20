package gui;

import javax.swing.*;
import java.awt.*;
// Need to refactor GamePanel to organize the layout and components for a Texas Hold'em Poker game GUI.
// increase the frame size to accommodate the game layout

public class GamePanel extends JPanel {
	public GamePanel() {
		// Set the background color
		setBackground(new Color(0, 128, 0)); // Green color for the poker table
		// Set the layout manager
		setLayout(new BorderLayout());
		
		 // Create a container for the top section
        JPanel topContainer = new JPanel(new GridBagLayout());
        topContainer.setBackground(getBackground());
        add(topContainer, BorderLayout.NORTH);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
		
		// Create a panel for player 2 (TOP)
		JPanel player2Cards = new JPanel();
		player2Cards.setBackground(getBackground());
		gbc.gridx = 1; // Center horizontally
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
		topContainer.add(player2Cards, gbc);
		
		// Create a container for the player 2
		JPanel p2CardsGroup = new JPanel();
		p2CardsGroup.setBackground(getBackground());
		player2Cards.add(p2CardsGroup);
		
		Card player2Card1 = new Card("card_back"); // Example card, replace with actual game logic
		p2CardsGroup.add(player2Card1.getCardLabel());
		Card player2Card2 = new Card("card_back"); // Example card, replace with actual game logic
		p2CardsGroup.add(player2Card2.getCardLabel());
		
		// Panel for player's balance
		RoundedPanel balancePanel = new RoundedPanel(20);
		balancePanel.setLayout(new GridBagLayout()); // Change layout to GridBagLayout
		balancePanel.setPreferredSize(new Dimension(150, 50)); // Set fixed size for the panel
		balancePanel.setBackground(Color.DARK_GRAY); // Dark background for the balance panel

		// Add content to the top-right panel (example: a label)
		JLabel balanceLabel = new JLabel("Balance: $1000"); // Example balance, replace with actual game logic
		balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
		balanceLabel.setForeground(Color.WHITE); // Set text color for visibility
		// GridBagConstraints for the label to be centered in the panel
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER; // Center the label
		balancePanel.add(balanceLabel, gbc);

		// Add the top-right panel to the NORTH position
		gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHEAST;
		topContainer.add(balancePanel, gbc);

		
		// Create a panel for player 1 (LEFT)
        // ---------------- Player 1 (Left, Bottom-Aligned) ----------------
        JPanel player1CardsContainer = new JPanel();
        player1CardsContainer.setLayout(new BoxLayout(player1CardsContainer, BoxLayout.Y_AXIS));
        player1CardsContainer.setBackground(getBackground());
        player1CardsContainer.add(Box.createRigidArea(new Dimension(0, 120))); // Adds 50px vertical space

        
        
        
		JPanel player1Cards = new JPanel();
		player1Cards.setPreferredSize(new Dimension(170, 0)); // Set fixed width
		player1Cards.setBackground(getBackground());
		player1CardsContainer.add(player1Cards);
		add(player1CardsContainer, BorderLayout.WEST);
				
		// Create a container for the player 1
		JPanel p1CardsGroup = new JPanel();
		p1CardsGroup.setBackground(getBackground());
		player1Cards.add(p1CardsGroup);
				
		Card player1Card1 = new Card("card_back"); // Example card, replace with actual game logic
		p1CardsGroup.add(player1Card1.getCardLabel());
		Card player1Card2 = new Card("card_back"); // Example card, replace with actual game logic
		p1CardsGroup.add(player1Card2.getCardLabel());
		
		// Create a panel for player 3 (RIGHT)
        JPanel player3CardsContainer = new JPanel();
        player3CardsContainer.setLayout(new BoxLayout(player3CardsContainer, BoxLayout.Y_AXIS));
        player3CardsContainer.setBackground(getBackground());
        player3CardsContainer.add(Box.createRigidArea(new Dimension(0, 120))); // Adds 50px vertical space
        
		JPanel player3Cards = new JPanel();
		player3Cards.setPreferredSize(new Dimension(170, 0)); // Set fixed width
		player3Cards.setBackground(getBackground());
        player3CardsContainer.add(player3Cards);
		add(player3CardsContainer, BorderLayout.EAST);
		
		// Create a container for the player 3
		JPanel p3CardsGroup = new JPanel();
		p3CardsGroup.setBackground(getBackground());
		player3Cards.add(p3CardsGroup);
		
		Card player3Card1 = new Card("card_back"); // Example card, replace with actual game logic
		p3CardsGroup.add(player3Card1.getCardLabel());
		Card player3Card2 = new Card("card_back"); // Example card, replace with actual game logic
		p3CardsGroup.add(player3Card2.getCardLabel());
		
		// Create a container for the center area
		JPanel centerContainer = new JPanel();
		centerContainer.setBackground(getBackground());
        add(centerContainer, BorderLayout.CENTER);
		
		// Create a panel for the river cards
		JPanel riverCardsPanel = new JPanel();
        riverCardsPanel.setLayout(new BoxLayout(riverCardsPanel, BoxLayout.Y_AXIS));
        riverCardsPanel.setBackground(getBackground());
		
        riverCardsPanel.add(Box.createRigidArea(new Dimension(0, 100))); // Push down
        
		// Import images for river cards
		JPanel riverCardsGroup = new JPanel();
		riverCardsGroup.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0)); // No gaps between cards
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
		
		riverCardsPanel.add(riverCardsGroup);
		centerContainer.add(riverCardsPanel);
		
		// Create a panel for the pot
		RoundedPanel potPanel = new RoundedPanel(20); // Rounded corners for the pot panel
		potPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for centering content
		potPanel.setPreferredSize(new Dimension(200, 100)); // Set fixed size for the pot panel
		potPanel.setBackground(Color.DARK_GRAY); // Dark background for the pot panel
		
		// Add a label for the pot amount
		JLabel potLabel = new JLabel("Pot: $0"); // Initial pot amount, replace with actual game logic
		potLabel.setForeground(Color.WHITE); // Set text color to white for visibility
		potLabel.setFont(new Font("Arial", Font.BOLD, 16));
		potPanel.add(potLabel, new GridBagConstraints());
		
		// Add the pot panel to the center container
		centerContainer.add(potPanel);
		
		// Create a container for the south border
        JPanel southContainer = new JPanel(new BorderLayout());
        southContainer.setBackground(getBackground());
        
        // Create a panel for player cards
        JPanel playerCardsPanel = new JPanel();
        playerCardsPanel.setLayout(new GridLayout(1, 2)); // No gaps between cards
        
        // Create another panel to group the cards together and be right next to each other
        JPanel PCardsGroup = new JPanel();
        PCardsGroup.setBackground(getBackground());
		
		// Import images for player cards
		Card playerCard1 = new Card("ace_of_spades"); // Example card, replace with actual game logic
		PCardsGroup.add(playerCard1.getCardLabel());
		Card playerCard2 = new Card("ace_of_hearts"); // Example card, replace with actual game logic
		PCardsGroup.add(playerCard2.getCardLabel());
		
        playerCardsPanel.add(PCardsGroup);
        southContainer.add(playerCardsPanel, BorderLayout.CENTER);
		
		// Create a button to start the game
		JButton startButton = new JButton("Start Game");
		southContainer.add(startButton, BorderLayout.SOUTH);
		
		 // Add the south container to the south border
        add(southContainer, BorderLayout.SOUTH);
        southContainer.setBackground(getBackground());

		
		startButton.addActionListener(e -> {
			// Add game logic here
		});
		
	}
}
