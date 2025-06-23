package poker.gui;

import poker.logic.GameManager;

import javax.swing.*;
import java.awt.*;

/**
 * A panel representing the top section of the game GUI.
 * This class extends BaseSectionPanel to provide a specific implementation for the top section,
 * including player cards and a balance panel.
 * * It initializes a player card panel with two cards and a balance panel displaying the player's balance.
 * * @returns A JPanel that displays player cards and a balance panel in the top section of the game GUI.
 */
public class TopSectionPanel extends BaseSectionPanel {
    private static final Dimension BALANCE_PANEL_SIZE = new Dimension(80, 25);
    private static final Font BALANCE_FONT = new Font("Arial", Font.PLAIN, 12);
    private static final Color BALANCE_COLOR = new Color(0, 0, 0, 150); // Semi-transparent black
    private static final Color TEXT_COLOR = Color.WHITE;
    
    private final GameManager gameManager;
    private final int playerIndex;
    private PlayerCardPanel playerCardPanel;
    private JPanel cardContainer;
    private JLabel balanceLabel;

    public TopSectionPanel(GameManager gameManager, int playerIndex) {
        this.gameManager = gameManager;
        this.playerIndex = playerIndex;
        setLayout(new GridBagLayout());

        JPanel cardBalanceContainer = new JPanel();
        cardBalanceContainer.setLayout(new GridBagLayout());
        cardBalanceContainer.setBackground(TABLE_COLOR);
        
        cardBalanceContainer.add(Box.createRigidArea(new Dimension(0, 10)), GridBagConstraintsFactory.createDefaultConstraints(0, 0));
        
        // Add balance panel for Player 2
        cardBalanceContainer.add(createBalancePanel(), GridBagConstraintsFactory.createDefaultConstraints(0, 1));

        // Add player 2 cards panel
        playerCardPanel = new PlayerCardPanel("card_back", "card_back", TABLE_COLOR);
        cardContainer = playerCardPanel.initializeCards();
        cardBalanceContainer.add(cardContainer, GridBagConstraintsFactory.createDefaultConstraints(0, 2));
        
        add(cardBalanceContainer, GridBagConstraintsFactory.createConstraints(
                1, 0, GridBagConstraints.CENTER, 0.0, 0.0, GridBagConstraints.BOTH
                ));

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
    
    /** Sets the player's cards face down. */
    public void hideCards() {
        playerCardPanel.setFaceDown();
    }

    /** Highlights this player's cards when it is their turn. */
    public void setHighlighted(boolean on) {
        playerCardPanel.setHighlighted(on);
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
    
    public void refreshBalance() {
        balanceLabel.setText("$" + gameManager.getPlayers().get(playerIndex).getChips());
    }
}
