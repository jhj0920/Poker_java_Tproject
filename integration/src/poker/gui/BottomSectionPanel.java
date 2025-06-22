package poker.gui;

import poker.logic.GameManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * JPanel that represents the bottom section of the game GUI.
 * Contains the player's cards and action buttons for starting the game and performing actions like Call, Raise, Fold, and All In.
 * @returns A JPanel with the player's cards and action buttons.
 */
public class BottomSectionPanel extends BaseSectionPanel {
	private static final Dimension START_BUTTON_SIZE = new Dimension(150, 50);
	private static final Dimension BUTTON_SIZE = new Dimension(100, 50);
	
    private final GameManager gameManager;
    private final int playerIndex;
    private final Runnable startCallback;
    private PlayerCardPanel playerCardsPanel;

    public BottomSectionPanel(GameManager gameManager, int playerIndex, Runnable startCallback) {
            this.gameManager = gameManager;
            this.playerIndex = playerIndex;
            this.startCallback = startCallback;
            setLayout(new GridBagLayout());
		
		// Add User's cards panel
		playerCardsPanel = new PlayerCardPanel("card_back", "card_back", TABLE_COLOR);
		add(playerCardsPanel.initializeCards(), GridBagConstraintsFactory.createDefaultConstraints(0, 0));
		
		// Create a panel for player actions
		JPanel playerActionsPanel = new JPanel();
		playerActionsPanel.setLayout(new GridBagLayout());
		playerActionsPanel.setBackground(TABLE_COLOR);
		
		// Create a button to start the game
		JButton startButton = new JButton("Start Game");
		startButton.setPreferredSize(START_BUTTON_SIZE);
		playerActionsPanel.add(startButton, GridBagConstraintsFactory.createDefaultConstraints(0, 0));
		
		// Add the player actions panel to the bottom section
		add(playerActionsPanel, GridBagConstraintsFactory.createDefaultConstraints(0, 1));
		
		// Button functionality
		startButton.addActionListener(e -> {
			// Logic to start the game
			gameManager.startNewRound();
            if (startCallback != null) startCallback.run();
			
			// Clear the playerActionsPanel
		    playerActionsPanel.removeAll();
		    
		 // Create buttons for player actions
			JButton callButton = createActionButton("Call");
			JButton raiseButton = createActionButton("Raise");
			JButton foldButton = createActionButton("Fold");
			JButton allInButton = createActionButton("All In");
			
			// Add buttons to the player actions panel
			playerActionsPanel.add(callButton, GridBagConstraintsFactory.createDefaultConstraints(0, 0));
			playerActionsPanel.add(raiseButton, GridBagConstraintsFactory.createDefaultConstraints(1, 0));
			playerActionsPanel.add(foldButton, GridBagConstraintsFactory.createDefaultConstraints(2, 0));
			playerActionsPanel.add(allInButton, GridBagConstraintsFactory.createDefaultConstraints(3, 0));
			
            // Add action listeners for the buttons
            callButton.addActionListener(new ButtonActionHandler("Call"));
            raiseButton.addActionListener(new ButtonActionHandler("Raise"));
            foldButton.addActionListener(new ButtonActionHandler("Fold"));
            allInButton.addActionListener(new ButtonActionHandler("All In"));
		    
		    // Refresh the panel to display the new buttons
		    playerActionsPanel.revalidate();
		    playerActionsPanel.repaint();
		});
    }
		
    /**
     * Refreshes the player's cards in the bottom section panel.
     */
    public void refreshCards() {
    	var cards = gameManager.getPlayers().get(playerIndex).getHand().getCards();
        if (cards.size() >= 2) {
        	playerCardsPanel.updateCards(cards.get(0), cards.get(1));
        }
	}
	
	private JButton createActionButton(String text) {
		JButton button = new JButton(text);
		button.setPreferredSize(BUTTON_SIZE);
		return button;
	}
	
    /**
	 * Action listener for the action buttons (Call, Raise, Fold, All In).
	 * Handles the button clicks and performs the corresponding actions.
	 */
    private class ButtonActionHandler implements ActionListener {
        private final String actionType;

        public ButtonActionHandler(String actionType) {
            this.actionType = actionType;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (actionType) {
                case "Call":
                	// Add logic for "Call" action
                    System.out.println("Call action triggered");
                    break;
                case "Raise":
                	// Add logic for "Raise" action
                    showRaiseDialog();
                    break;
                case "Fold":
                	// Add logic for "Fold" action
                    System.out.println("Fold action triggered");
                    break;
                case "All In":
                	// Add logic for "All In" action
                    System.out.println("All In action triggered");
                    break;
                default:
                    throw new IllegalArgumentException("Unknown action type: " + actionType);
            }
        }

        /**
		 * Shows a dialog for selecting the raise amount using a slider.
		 * The dialog contains a label to display the current raise amount,
		 * a slider to select the amount, and a button to confirm the selection.
		 */
        private void showRaiseDialog() {
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
            raiseSlider.addChangeListener(event -> {
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
        }
    }
}
