package poker.gui;

import poker.logic.logicCard;
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
    private Card guiCard1;
    private Card guiCard2;

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

        guiCard1 = addCard(card1Image);
        guiCard2 = addCard(card2Image);
        cardGroup.add(guiCard1.getCardLabel());
        cardGroup.add(guiCard2.getCardLabel());

        return cardPanel;
    }
    
    /**
	 * Updates the images of the player's cards.
	 * @param card1 The first card to update.
	 * @param card2 The second card to update.
	 */
    public void updateCards(logicCard card1, logicCard card2) {
        if (card1 == null || card2 == null) {
            setFaceDown();
            return;
        }
        card1Image = convertCard(card1);
        card2Image = convertCard(card2);
        guiCard1.setFrontCard(card1Image);
        guiCard2.setFrontCard(card2Image);
    }
    
    /** Sets both cards to show their backs. */
    public void setFaceDown() {
        guiCard1.setFaceDown();
        guiCard2.setFaceDown();
    }
    
    /** @return true if the cards are currently face up. */
    public boolean areCardsFaceUp() {
        return guiCard1.isFaceUp() && guiCard2.isFaceUp();
    }



    /**
	 * Converts a poker logic card to a string representation for the GUI.
	 * @param card The poker logic card to convert.
	 * @returns A string representing the card in the format "rank_of_suit".
	 */
    private String convertCard(logicCard card) {
        String rank;
        switch (card.getRank()) {
            case TWO -> rank = "2";
            case THREE -> rank = "3";
            case FOUR -> rank = "4";
            case FIVE -> rank = "5";
            case SIX -> rank = "6";
            case SEVEN -> rank = "7";
            case EIGHT -> rank = "8";
            case NINE -> rank = "9";
            case TEN -> rank = "10";
            case JACK -> rank = "jack";
            case QUEEN -> rank = "queen";
            case KING -> rank = "king";
            case ACE -> rank = "ace";
            default -> rank = "";
        }
        return rank + "_of_" + card.getSuit().name().toLowerCase();
    }
}
