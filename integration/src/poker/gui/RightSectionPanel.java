package poker.gui;

import poker.logic.GameManager;
import javax.swing.*;
import java.awt.*;

/**
 * A panel representing the right section of the game GUI.
 * This class extends BaseSectionPanel to provide a specific implementation for displaying player cards.
 * It initializes a player card panel with two cards and adds it to the right side of the section.
 * * @returns A JPanel that displays player cards in the right section of the game GUI.
 */
public class RightSectionPanel extends BaseSectionPanel {
    private static final Dimension PANEL_SIZE = new Dimension(170, 0); // Example size, adjust as needed
    private static final Dimension BALANCE_PANEL_SIZE = new Dimension(80, 25);
    private static final Font BALANCE_FONT = new Font("Arial", Font.PLAIN, 12);
    private static final Color BALANCE_COLOR = new Color(0, 0, 0, 150); // Semi-transparent black
    private static final Color TEXT_COLOR = Color.WHITE;
    private final GameManager gameManager;
    private final int playerIndex;
    private PlayerCardPanel playerCardPanel;
    private JPanel cardContainer;
    private JLabel balanceLabel;

    public RightSectionPanel(GameManager gameManager, int playerIndex) {
        this.gameManager = gameManager;
        this.playerIndex = playerIndex;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        JPanel player2CardsContainer = new JPanel();
        player2CardsContainer.setLayout(new BoxLayout(player2CardsContainer, BoxLayout.Y_AXIS));
        player2CardsContainer.setBackground(TABLE_COLOR);
        
        // Add vertical space at the top of the panel
        player2CardsContainer.add(Box.createRigidArea(new Dimension(0, 120))); // Adds 120px vertical space
        
        // Add balance panel for Player 1
        JPanel balancePanel = createBalancePanel();
        balancePanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment
        player2CardsContainer.add(balancePanel);  

        // Add player cards panel
        playerCardPanel = new PlayerCardPanel("card_back", "card_back", TABLE_COLOR);
        cardContainer = playerCardPanel.initializeCards();
        cardContainer.setPreferredSize(PANEL_SIZE);
        cardContainer.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment
        player2CardsContainer.add(cardContainer);
        
		add(player2CardsContainer);
	}
    
    public void refreshCards(boolean showCards) {
        var player = gameManager.getPlayers().get(playerIndex);
        var cards = player.getHand().getCards();
        if (cards.size() >= 2) {
        	if (showCards) {
                playerCardPanel.updateCards(cards.get(0), cards.get(1));
            } else {
                playerCardPanel.setFaceDown();
            }
        }
        playerCardPanel.setDead(player.isFolded() || player.getChips() <= 0);
    }
    
    /** Sets this player's cards face down. */
    public void hideCards() {
        playerCardPanel.setFaceDown();
    }

    /** Highlights this player's cards when it is their turn. */
    public void setHighlighted(boolean on) {
        playerCardPanel.setHighlighted(on);
    }
    
    public void refreshBalance() {
        balanceLabel.setText("$" + gameManager.getPlayers().get(playerIndex).getChips());
	}
	
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
