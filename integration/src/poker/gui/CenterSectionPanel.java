package poker.gui;

import poker.logic.GameManager;
import poker.logic.Pot;
import poker.logic.logicCard;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Arrays;

/**
 * A panel that represents the center section of a Texas Hold'em Poker game GUI.
 * It contains the river cards and the pot display.
 * This class extends BaseSectionPanel to inherit common properties and methods for section panels.
 * @return A JPanel that displays the river cards and the pot amount.
 */
public class CenterSectionPanel extends BaseSectionPanel{
		private GameManager gameManager; // Reference to the GameManager for game logic
		private final int playerIndex = 3; // Index of the player for whom the balance is displayed (0-based index)
		private RiverCardPanel riverCardPanel;
		private JLabel potLabel;
		private JLabel balanceLabel;
	
		private static final Dimension POT_PANEL_SIZE = new Dimension(200, 100);
	    private static final Font POT_FONT = new Font("Arial", Font.BOLD, 16);
	    private static final Color POT_COLOR = Color.DARK_GRAY;
	    private static final Color TEXT_COLOR = Color.WHITE;
        private static final Dimension BALANCE_PANEL_SIZE = new Dimension(150, 50);
        private static final Font BALANCE_FONT = new Font("Arial", Font.BOLD, 16);
        private static final Color BALANCE_COLOR = Color.DARK_GRAY;

	public CenterSectionPanel(GameManager gameManager) {
		this.gameManager = gameManager; // Initialize the GameManager reference
		setBackground(TABLE_COLOR);
		setLayout(new BorderLayout());
        
		JPanel centerContainer = new JPanel();
		centerContainer.setBackground(TABLE_COLOR);
		centerContainer.setLayout(new GridBagLayout()); // Use BoxLayout for vertical stacking


		// Create a panel for the river cards
		
		riverCardPanel = new RiverCardPanel(
				Arrays.asList("card_back", "card_back", "card_back", "card_back", "card_back"),
				TABLE_COLOR
		);
		centerContainer.add(
				riverCardPanel.initializeCards(),
				GridBagConstraintsFactory.createConstraints(0, 0, GridBagConstraints.CENTER, 0.0, 0.0, GridBagConstraints.NONE)
		); // Add river cards panel to the center section

		// Create a panel for the pot
		centerContainer.add(
				createPotPanel(),
				GridBagConstraintsFactory.createConstraints(0, 1, GridBagConstraints.CENTER, 0.0, 0.0, GridBagConstraints.NONE)
		); // Center the pot panel
		centerContainer.add(Box.createRigidArea(new Dimension(0, 10)), GridBagConstraintsFactory.createConstraints(0, 2, GridBagConstraints.CENTER, 0.0, 0.0, GridBagConstraints.NONE));
		centerContainer.add(createBalancePanel(), GridBagConstraintsFactory.createConstraints(0, 3, GridBagConstraints.CENTER, 0.0, 0.0, GridBagConstraints.NONE));

		add(centerContainer, BorderLayout.CENTER); // Add center container to the center section
	}
	
	/**
	 * Creates a panel to display the pot amount.
	 * This panel is styled with a rounded border and contains a label showing the pot amount.
	 * @return A JPanel containing the pot display.
	 */
	private JPanel createPotPanel() {
		RoundedPanel potPanel = new RoundedPanel(20);
		potPanel.setLayout(new GridBagLayout());
		potPanel.setPreferredSize(POT_PANEL_SIZE);
		potPanel.setBackground(POT_COLOR);

		potLabel = new JLabel("Pot: $0"); // Example pot amount, replace with actual game logic
		potLabel.setFont(POT_FONT);
		potLabel.setForeground(TEXT_COLOR);
		potPanel.add(potLabel, GridBagConstraintsFactory.createDefaultConstraints(0, 0));

		return potPanel;
	}
	
    public void refreshBalance() {
        balanceLabel.setText("Balance: $" + gameManager.getPlayers().get(playerIndex).getChips());
	}

	private JPanel createBalancePanel() {
	        RoundedPanel balancePanel = new RoundedPanel(20);
	        balancePanel.setLayout(new GridBagLayout());
	        balancePanel.setPreferredSize(BALANCE_PANEL_SIZE);
	        balancePanel.setBackground(BALANCE_COLOR);
	
	        balanceLabel = new JLabel();
	        balanceLabel.setFont(BALANCE_FONT);
	        balanceLabel.setForeground(TEXT_COLOR);
	        balancePanel.add(balanceLabel, GridBagConstraintsFactory.createDefaultConstraints(0, 0));
	
	        refreshBalance();
	
	        return balancePanel;
	}
	
	/**
	 * Updates the community cards displayed in the river card panel.
	 */
    private void updateCommunityCards() {
        List<logicCard> communityCards = gameManager.getCommunityCards();
        // Logic to update GUI with community cards
        riverCardPanel.updateCards(communityCards);
    }

    /**
	 * Refreshes the community cards displayed in the river card panel.
	 * This method can be called to update the display when the game state changes.
	 */
    public void refreshCommunityCards() {
        updateCommunityCards();
    }
    

    /** Sets all community cards face down. */
    public void hideCommunityCards() {
        riverCardPanel.setFaceDown();
    }
    
    public void refreshPot() {
        Pot pot = gameManager.getPot();
        int main = pot.getSmallPot();
        int side = pot.getBigPot();
        if (side > 0) {
            potLabel.setText("Pot: $" + main + "\n Side: $" + side);
        } else {
            potLabel.setText("Pot: $" + main);
        }
    }
}
