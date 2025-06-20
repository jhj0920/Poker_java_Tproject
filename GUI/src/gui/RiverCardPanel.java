package gui;

import java.awt.Color;
import java.util.List;

import javax.swing.JPanel;

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
