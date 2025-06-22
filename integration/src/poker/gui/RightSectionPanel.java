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
    private final GameManager gameManager;
    private final int playerIndex;
    private PlayerCardPanel playerCardPanel;

    public RightSectionPanel(GameManager gameManager, int playerIndex) {
        this.gameManager = gameManager;
        this.playerIndex = playerIndex;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel player2CardsContainer = new JPanel();
		player2CardsContainer.setLayout(new BoxLayout(player2CardsContainer, BoxLayout.Y_AXIS));
		player2CardsContainer.setBackground(TABLE_COLOR);
		player2CardsContainer.add(Box.createRigidArea(new Dimension(0, 120))); // Adds 120px vertical space

        playerCardPanel = new PlayerCardPanel("card_back", "card_back", TABLE_COLOR);
        JPanel playerCards = playerCardPanel.initializeCards();
        playerCards.setPreferredSize(PANEL_SIZE);
        player2CardsContainer.add(playerCards);

		add(player2CardsContainer, BorderLayout.EAST);
	}
    
    public void refreshCards() {
        var cards = gameManager.getPlayers().get(playerIndex).getHand().getCards();
        if (cards.size() >= 2) {
                playerCardPanel.updateCards(cards.get(0), cards.get(1));
        }
    }

}
