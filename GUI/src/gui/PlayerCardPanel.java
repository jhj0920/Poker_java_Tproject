package gui;

import javax.swing.*;
import java.awt.*;

/**
 * A panel representing a player's cards in a card game.
 * This class extends CardPanel to provide a specific implementation for displaying player cards.
 * It initializes two cards with specified images and a background color.
 * @param card1Image The image for the first card.
 * @param card2Image The image for the second card.
 * @param backgroundColor The background color for the card panel.
 * @returns A JPanel that displays two player cards with the specified images and background color.
 */
public class PlayerCardPanel extends CardPanel {
    private String card1Image;
    private String card2Image;

    public PlayerCardPanel(String card1Image, String card2Image, Color backgroundColor) {
        super(backgroundColor);
        this.card1Image = card1Image;
        this.card2Image = card2Image;
        initializeCards();
    }

    @Override
    protected JPanel initializeCards() {
        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(backgroundColor);

        JPanel cardGroup = new JPanel();
        cardGroup.setBackground(backgroundColor);
        cardPanel.add(cardGroup);

        cardGroup.add(addCard(card1Image).getCardLabel());
        cardGroup.add(addCard(card2Image).getCardLabel());

        return cardPanel;
    }
}
