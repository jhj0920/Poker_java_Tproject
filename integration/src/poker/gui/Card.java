package poker.gui;
import javax.swing.*;
import java.awt.event.*;

/* 
 * Represents a playing card in the GUI.
 * The card can be flipped to show its front or back image.
 * Clicking on the card toggles its face-up state.
 * @param frontCard The identifier for the front image of the card (e.g., "ace_of_hearts").
 * @returns A JLabel that displays the card image.
 */
public class Card extends JLabel {
	protected String frontCard; // e.g., "ace_of_hearts"
	protected final JLabel cardLabel; // JLabel to display the card image
	protected boolean isFaceUp; // Flag to check if the card is face up
	
	public Card(String frontCard) {
		this.frontCard = frontCard;
		this.cardLabel = new JLabel(CardImageLoader.getCard("card_back")); // Initially set to the back of the card
		this.isFaceUp = false; // Initially, the card is face down
		
		// Add mouse listener to toggle the card face on click
		this.cardLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				flip(); // Toggle the card face on click
			}
		});
	}
	
	public void flip() {
		// Bug where the card does not flip if clicked multiple times quickly
	        if (isFaceUp) {
	            cardLabel.setIcon(CardImageLoader.getCard("card_back"));
	            isFaceUp = false;
	    } else {
	            cardLabel.setIcon(CardImageLoader.getCard(frontCard));
	            isFaceUp = true;
	    }
	}
	
	public JLabel getCardLabel() {
		return cardLabel; // Return the JLabel to be added to the GUI
	}
	
	public String getFrontCard() {
        return frontCard; // Return the name of the front card
	}
	
    /**
     * Updates the image shown on this card and marks it as face up.
     *
     * @param frontCard the image identifier, e.g. "ace_of_spades"
     */
    public void setFrontCard(String frontCard) {
            this.frontCard = frontCard;
            this.cardLabel.setIcon(CardImageLoader.getCard(frontCard));
            this.isFaceUp = true;
            revalidate();
            repaint();
    }
}
