package poker.gui;

import poker.logic.GameManager;
import poker.logic.BettingRoundManager;
import poker.logic.Player;
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
    private final BettingRoundManager bettingManager;
    private PlayerCardPanel playerCardsPanel;

    public BottomSectionPanel(GameManager gameManager, BettingRoundManager bettingManager, int playerIndex, Runnable startCallback) {
            this.gameManager = gameManager;
            this.bettingManager = bettingManager;
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
			bettingManager.reset();
            if (startCallback != null) startCallback.run();
			
			// Clear the playerActionsPanel
		    playerActionsPanel.removeAll();
		    
		 // Create buttons for player actions
			JButton callButton = createActionButton("Call");
			JButton raiseButton = createActionButton("Raise");
			JButton foldButton = createActionButton("Fold");
			JButton checkButton = createActionButton("Check");
			JButton allInButton = createActionButton("All In");
			
			// Add buttons to the player actions panel
			playerActionsPanel.add(callButton, GridBagConstraintsFactory.createDefaultConstraints(0, 0));
			playerActionsPanel.add(raiseButton, GridBagConstraintsFactory.createDefaultConstraints(1, 0));
			playerActionsPanel.add(foldButton, GridBagConstraintsFactory.createDefaultConstraints(2, 0));
			playerActionsPanel.add(checkButton, GridBagConstraintsFactory.createDefaultConstraints(3, 0));
			playerActionsPanel.add(allInButton, GridBagConstraintsFactory.createDefaultConstraints(4, 0));
			
            // Add action listeners for the buttons
            callButton.addActionListener(new ButtonActionHandler("Call"));
            raiseButton.addActionListener(new ButtonActionHandler("Raise"));
            foldButton.addActionListener(new ButtonActionHandler("Fold"));
            checkButton.addActionListener(new ButtonActionHandler("Check"));
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
        	Player player = gameManager.getPlayers().get(playerIndex);
            String error = null;
            switch (actionType) {
	            case "Call" -> {
	                error = bettingManager.call(player);
	                if (error == null && startCallback != null) startCallback.run();
	            }
	            case "Raise" -> showRaiseDialog(player);
	            case "Fold" -> {
	                bettingManager.fold(player);
	                if (startCallback != null) startCallback.run();
	            }
	            case "Check" -> {
	                error = bettingManager.check(player);
	                if (error == null && startCallback != null) startCallback.run();
	            }
	            case "All In" -> {
	                error = bettingManager.allIn(player);
	                if (error == null && startCallback != null) startCallback.run();
	            }
	            default -> throw new IllegalArgumentException("Unknown action type: " + actionType);
	        }
	
	        if (error != null) {
	            JOptionPane.showMessageDialog(BottomSectionPanel.this, error, "Invalid Action", JOptionPane.WARNING_MESSAGE);
            }
        }

        /**
		 * Shows a dialog for selecting the raise amount using a slider.
		 * The dialog contains a label to display the current raise amount,
		 * a slider to select the amount, and a button to confirm the selection.
		 */
        private void showRaiseDialog(Player player) {
        	// Create a dialog for the raise slider
            JDialog raiseDialog = new JDialog((Frame) null, "Raise Amount", true);
            raiseDialog.setLayout(new BorderLayout());
            raiseDialog.setSize(300, 150);
            raiseDialog.setLocationRelativeTo(null); // Center the dialog on the screen

            // Create a label to display the current raise amount
            JLabel raiseLabel = new JLabel("Raise Amount: $0", JLabel.CENTER);
            raiseLabel.setFont(new Font("Arial", Font.BOLD, 16));
            raiseDialog.add(raiseLabel, BorderLayout.NORTH);
            
            int minRaise = bettingManager.getMinimumRaiseAmount();
            int maxRaise = player.getChips() - bettingManager.getAmountToCall(player);
            if (maxRaise < minRaise) {
                JOptionPane.showMessageDialog(raiseDialog,
                        "최소 레이즈 금액보다 칩이 부족합니다.",
                        "Invalid Action", JOptionPane.WARNING_MESSAGE);
                raiseDialog.dispose();
                return;
            }

            // Create a slider for selecting the raise amount
            JSlider raiseSlider = new JSlider(minRaise, maxRaise, minRaise);
            int major = Math.max(1, (maxRaise - minRaise) / 5);
            raiseSlider.setMajorTickSpacing(major);
            raiseSlider.setMinorTickSpacing(Math.max(1, major / 4));
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
                int raiseTo = bettingManager.getCurrentBet() + raiseAmount;
                String err = bettingManager.raise(player, raiseTo);
                if (err == null) {
                    if (startCallback != null) startCallback.run();
                    raiseDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(raiseDialog, err, "Invalid Action", JOptionPane.WARNING_MESSAGE);
                }
            });
            raiseDialog.add(confirmButton, BorderLayout.SOUTH);

            // Show the dialog
            raiseDialog.setVisible(true);
        }
    }
}
