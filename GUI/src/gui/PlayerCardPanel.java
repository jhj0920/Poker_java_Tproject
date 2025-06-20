package gui;

import javax.swing.*;
import java.awt.*;

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
