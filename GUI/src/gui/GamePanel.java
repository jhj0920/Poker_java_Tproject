package gui;

import javax.swing.*;
import java.awt.*;
// Need to refactor GamePanel to organize the layout and components for a Texas Hold'em Poker game GUI.

public class GamePanel extends JPanel {
    private static final Color TABLE_COLOR = new Color(0, 128, 0); // Green color for the poker table
    
	public GamePanel() {
		setBackground(TABLE_COLOR);
		setLayout(new BorderLayout());
		
		// ---------------- Top Section (Player 2, Balance) ----------------
        TopSectionPanel topSectionPanel = new TopSectionPanel();
        add(topSectionPanel, BorderLayout.NORTH);
        
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
		
        // Create a panel for the player actions
        JPanel playerActionsPanel = new JPanel();
        playerActionsPanel.setLayout(new GridBagLayout());
        playerActionsPanel.setBackground(getBackground());
		// Create a button to start the game
		JButton startButton = new JButton("Start Game");
		startButton.setLayout(new GridBagLayout());
		startButton.setPreferredSize(new Dimension(150, 50));
		playerActionsPanel.add(startButton, new GridBagConstraints());
		
		
		// Add the player actions panel to the south container
		southContainer.add(playerActionsPanel, BorderLayout.SOUTH);
		
		 // Add the south container to the south border
        add(southContainer, BorderLayout.SOUTH);
        southContainer.setBackground(getBackground());

		
		startButton.addActionListener(e -> {
			// Add game logic here
		    // Clear the playerActionsPanel
		    playerActionsPanel.removeAll();

		    // Create the "Call" button
		    JButton callButton = new JButton("Call");
		    callButton.setPreferredSize(new Dimension(100, 50));
		    callButton.addActionListener(event -> {
		        // Add logic for "Call" action
		        System.out.println("Call action triggered");
		    });
		    playerActionsPanel.add(callButton, new GridBagConstraints());

		    // Create the "Raise" button
		    JButton raiseButton = new JButton("Raise");
		    raiseButton.setPreferredSize(new Dimension(100, 50));
		    raiseButton.addActionListener(event -> {
		        // Create a dialog for the raise slider
		        JDialog raiseDialog = new JDialog((Frame) null, "Raise Amount", true);
		        raiseDialog.setLayout(new BorderLayout());
		        raiseDialog.setSize(300, 150);
		        raiseDialog.setLocationRelativeTo(null); // Center the dialog on the screen

		        // Create a label to display the current raise amount
		        JLabel raiseLabel = new JLabel("Raise Amount: $0", JLabel.CENTER);
		        raiseLabel.setFont(new Font("Arial", Font.BOLD, 16));
		        raiseDialog.add(raiseLabel, BorderLayout.NORTH);

		        // Create a slider for selecting the raise amount
		        JSlider raiseSlider = new JSlider(0, 1000, 0); // Min: 0, Max: 1000, Initial: 0
		        raiseSlider.setMajorTickSpacing(200);
		        raiseSlider.setMinorTickSpacing(50);
		        raiseSlider.setPaintTicks(true);
		        raiseSlider.setPaintLabels(true);
		        raiseDialog.add(raiseSlider, BorderLayout.CENTER);

		        // Add a listener to update the label as the slider value changes
		        raiseSlider.addChangeListener(e2 -> {
		            int raiseAmount = raiseSlider.getValue();
		            raiseLabel.setText("Raise Amount: $" + raiseAmount);
		        });
		        

		        // Add a button to confirm the raise amount
		        JButton confirmButton = new JButton("Confirm");
		        confirmButton.addActionListener(confirmEvent -> {
		            int raiseAmount = raiseSlider.getValue();
		            System.out.println("Raise confirmed: $" + raiseAmount);
		            raiseDialog.dispose(); // Close the dialog
		        });
		        raiseDialog.add(confirmButton, BorderLayout.SOUTH);

		        // Show the dialog
		        raiseDialog.setVisible(true);
		    });
		    playerActionsPanel.add(raiseButton, new GridBagConstraints());

		    // Create the "Fold" button
		    JButton foldButton = new JButton("Fold");
		    foldButton.setPreferredSize(new Dimension(100, 50));
		    foldButton.addActionListener(event -> {
		        // Add logic for "Fold" action
		        System.out.println("Fold action triggered");
		    });
		    playerActionsPanel.add(foldButton, new GridBagConstraints());

		    // Create the "All In" button
		    JButton allInButton = new JButton("All In");
		    allInButton.setPreferredSize(new Dimension(100, 50));
		    allInButton.addActionListener(event -> {
		        // Add logic for "All In" action
		        System.out.println("All In action triggered");
		    });
		    playerActionsPanel.add(allInButton, new GridBagConstraints());

		    // Refresh the panel to display the new buttons
		    playerActionsPanel.revalidate();
		    playerActionsPanel.repaint();
		});
		
		
	}
}
