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
    private static final Dimension BALANCE_PANEL_SIZE = new Dimension(150, 50);
    private static final Font BALANCE_FONT = new Font("Arial", Font.BOLD, 16);
    private static final Color BALANCE_COLOR = Color.DARK_GRAY;
    private static final Color TEXT_COLOR = Color.WHITE;
    
    private final GameManager gameManager;
    private final int playerIndex;
    private PlayerCardPanel playerCardPanel;

    public TopSectionPanel(GameManager gameManager, int playerIndex) {
        this.gameManager = gameManager;
        this.playerIndex = playerIndex;
        setLayout(new GridBagLayout());

        // Add player 2 cards panel
        playerCardPanel = new PlayerCardPanel("card_back", "card_back", TABLE_COLOR);
        add(playerCardPanel.initializeCards(), GridBagConstraintsFactory.createDefaultConstraints(1, 0));

        // Add balance panel
        add(createBalancePanel(), GridBagConstraintsFactory.createConstraints(1, 0, GridBagConstraints.NORTHEAST, 0.0, 0.0, GridBagConstraints.NONE));
    }
    
    public void refreshCards() {
        var cards = gameManager.getPlayers().get(playerIndex).getHand().getCards();
        if (cards.size() >= 2) {
            playerCardPanel.updateCards(cards.get(0), cards.get(1));
        }
    }
    
    private JPanel createBalancePanel() {
        RoundedPanel balancePanel = new RoundedPanel(20);
        balancePanel.setLayout(new GridBagLayout());
        balancePanel.setPreferredSize(BALANCE_PANEL_SIZE);
        balancePanel.setBackground(BALANCE_COLOR);

        JLabel balanceLabel = new JLabel("Balance: $1000"); // Example balance, replace with actual game logic
        balanceLabel.setFont(BALANCE_FONT);
        balanceLabel.setForeground(TEXT_COLOR);
        balancePanel.add(balanceLabel, GridBagConstraintsFactory.createDefaultConstraints(0, 0));

        return balancePanel;
    }
}
