package gui;

import javax.swing.*;
import java.awt.*;

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
