package poker.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import poker.logic.logicCard;

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
    private List<Card> guiCards;

    // Constructor to initialize the river card panel with a list of card images and a background color.
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

        guiCards = new ArrayList<>();
        for (String cardImage : cardImages) {
        	Card c = addCard(cardImage);
            guiCards.add(c);
            cardGroup.add(c.getCardLabel());
        }
        
        return cardPanel;
    }
    
    /**
	 * Updates the displayed cards with new card data.
	 * This method replaces the current card images with new ones based on the provided list of cards.
	 * @param cards A list of poker.logic.Card objects representing the new cards to display.
	 */
    public void updateCards(List<logicCard> cards) {
        cardImages = new ArrayList<>();
        for (int i = 0; i < guiCards.size(); i++) {
            if (i < cards.size()) {
                String img = convertCard(cards.get(i));
                cardImages.add(img);
                guiCards.get(i).setFrontCard(img);
            } else {
                cardImages.add("card_back");
                guiCards.get(i).setFaceDown();
            }
        }
    }
    
    /** Sets all river cards to show their backs. */
    public void setFaceDown() {
        for (Card c : guiCards) {
            c.setFaceDown();
        }
    }


    /**
	 * Converts a poker.logic.Card object to a string representation for the card image.
	 * This method maps the card's rank and suit to a specific image file name format.
	 * @param card The poker.logic.Card object to convert.
	 * @return A string representing the card image file name.
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
