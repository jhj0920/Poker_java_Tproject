package gui;

import javax.swing.*;
import java.awt.*;

/**
 * An abstract class representing a panel for displaying cards in a card game.
 * This class provides a common structure for card panels, including background color and layout.
 * Specific implementations should define how cards are initialized and displayed.
 * @param backgroundColor The background color for the card panel.
 * @returns A JPanel with a predefined background color and layout for displaying cards.
 */
public abstract class CardPanel extends JPanel {
    protected Color backgroundColor;

    public CardPanel(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        setBackground(backgroundColor);
        setLayout(new FlowLayout());
    }

    protected abstract JPanel initializeCards();

    protected Card addCard(String cardImage) {
        Card card = new Card(cardImage); // Replace with actual game logic
        return card;
    }
}
