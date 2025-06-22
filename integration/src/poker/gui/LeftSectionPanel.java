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
    private final GameManager gameManager;
    private final int playerIndex;
    private PlayerCardPanel playerCardPanel;

    public LeftSectionPanel(GameManager gameManager, int playerIndex) {
            this.gameManager = gameManager;
            this.playerIndex = playerIndex;
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
        JPanel player1CardsContainer = new JPanel();
        player1CardsContainer.setLayout(new BoxLayout(player1CardsContainer, BoxLayout.Y_AXIS));
        player1CardsContainer.setBackground(TABLE_COLOR);
        player1CardsContainer.add(Box.createRigidArea(new Dimension(0, 120))); // Adds 120px vertical space

        playerCardPanel = new PlayerCardPanel("card_back", "card_back", TABLE_COLOR);
        JPanel playerCards = playerCardPanel.initializeCards();
        playerCards.setPreferredSize(PANEL_SIZE);
        player1CardsContainer.add(playerCards);
		
		add(player1CardsContainer, BorderLayout.WEST);
	}
    
    public void refreshCards() {
        var cards = gameManager.getPlayers().get(playerIndex).getHand().getCards();
        if (cards.size() >= 2) {
                playerCardPanel.updateCards(cards.get(0), cards.get(1));
        }
    }
}
