package poker.gui;

import java.awt.Color;
import java.util.List;

import javax.swing.JPanel;

/**
 * A panel representing the river cards in a card game.
 * This class extends CardPanel to provide a specific implementation for displaying river cards.
 * It initializes multiple cards with specified images and a background color.
 * @param cardImages A list of image paths for the river cards.
 * @param backgroundColor The background color for the card panel.
 * @returns A JPanel that displays river cards with the specified images and background color.
 */
public class RiverCardPanel extends CardPanel {
    private List<String> cardImages;

    public RiverCardPanel(List<String> cardImages, Color backgroundColor) {
        super(backgroundColor);
        this.cardImages = cardImages;
        initializeCards();
    }

    @Override
    protected JPanel initializeCards() {
        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(backgroundColor);

        JPanel cardGroup = new JPanel();
        cardGroup.setBackground(backgroundColor);
        cardPanel.add(cardGroup);

        
        for (String cardImage : cardImages) {
            cardGroup.add(addCard(cardImage).getCardLabel());
        }
        
        return cardPanel;
    }
}
