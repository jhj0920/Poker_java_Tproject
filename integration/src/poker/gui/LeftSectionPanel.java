package poker.gui;

import poker.logic.GameManager;
import javax.swing.*;
import java.awt.*;

/**
 * A panel representing the left section of the game GUI.
 * This panel contains player cards and is designed to be used in a card game interface.
 * It extends BaseSectionPanel to inherit common properties and methods.
 * @returns A JPanel that displays player cards in the left section of the game GUI.
 */
public class LeftSectionPanel extends BaseSectionPanel{
    private static final Dimension PANEL_SIZE = new Dimension(170, 0); // Example size, adjust as needed
    private static final Dimension BALANCE_PANEL_SIZE = new Dimension(80, 25);
    private static final Font BALANCE_FONT = new Font("Arial", Font.PLAIN, 12);
    private static final Color BALANCE_COLOR = new Color(0, 0, 0, 150); // Semi-transparent black
    private static final Color TEXT_COLOR = Color.WHITE;
    private final GameManager gameManager;
    private final int playerIndex;
    private PlayerCardPanel playerCardPanel;
    private JLabel balanceLabel;

    public LeftSectionPanel(GameManager gameManager, int playerIndex) {
            this.gameManager = gameManager;
            this.playerIndex = playerIndex;
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
        JPanel player1CardsContainer = new JPanel();
        player1CardsContainer.setLayout(new BoxLayout(player1CardsContainer, BoxLayout.Y_AXIS));
        player1CardsContainer.setBackground(TABLE_COLOR);
        
        // Add vertical space at the top of the panel
        player1CardsContainer.add(Box.createRigidArea(new Dimension(0, 120))); // Adds 120px vertical space
      
        // Add balance panel for Player 1
        JPanel balancePanel = createBalancePanel();
        balancePanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment
        player1CardsContainer.add(balancePanel);     
        
        // Add player cards panel
        playerCardPanel = new PlayerCardPanel("card_back", "card_back", TABLE_COLOR);
        JPanel playerCards = playerCardPanel.initializeCards();
        playerCards.setPreferredSize(PANEL_SIZE);
        playerCards.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment
        player1CardsContainer.add(playerCards);
       
		
		add(player1CardsContainer);
	}
    
    public void refreshCards() {
        var cards = gameManager.getPlayers().get(playerIndex).getHand().getCards();
        if (cards.size() >= 2) {
                playerCardPanel.updateCards(cards.get(0), cards.get(1));
        }
    }
    
    /**
	 * Refreshes the balance label to display the current player's chip count.
	 */
    public void refreshBalance() {
        balanceLabel.setText("$" + gameManager.getPlayers().get(playerIndex).getChips());
    }
	
    /**
     * Creates a panel to display the player's balance.
     * @return A JPanel containing the balance display.
     */
	private JPanel createBalancePanel() {
	        RoundedPanel balancePanel = new RoundedPanel(20);
	        balancePanel.setLayout(new GridBagLayout());
	        balancePanel.setPreferredSize(BALANCE_PANEL_SIZE);
            balancePanel.setMaximumSize(BALANCE_PANEL_SIZE);
            balancePanel.setMinimumSize(BALANCE_PANEL_SIZE);
	        balancePanel.setBackground(BALANCE_COLOR);
	
	        balanceLabel = new JLabel();
	        balanceLabel.setFont(BALANCE_FONT);
	        balanceLabel.setForeground(TEXT_COLOR);
	        balancePanel.add(balanceLabel, GridBagConstraintsFactory.createDefaultConstraints(0, 0));
	
	        refreshBalance();
	
	        return balancePanel;
	}
}
