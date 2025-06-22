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
	protected final String frontCard; // e.g., "ace_of_hearts"
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
		// Bug where the card flips back to face down after the first click
		// Change if else statement to switch statement?
		if (isFaceUp) {
			Timer timer = new Timer(150, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					cardLabel.setIcon(CardImageLoader.getCard(frontCard)); // Show the front of the card after 0.15 second
				}
			});
			timer.setRepeats(false); // Only execute once
			timer.start(); // Start the timer
			isFaceUp = !isFaceUp; // Toggle the face-up state
		} else {
			cardLabel.setIcon(CardImageLoader.getCard(frontCard)); // Show the front of the card
			// Flip the card back to face down after 0.15 second
			Timer timer = new Timer(150, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					cardLabel.setIcon(CardImageLoader.getCard("card_back")); // Show the front of the card after 0.15 second
				}
			});
			timer.setRepeats(false); // Only execute once
			timer.start(); // Start the timer
			isFaceUp = !isFaceUp; // Toggle the face-up state
		}
	}
	
	public JLabel getCardLabel() {
		return cardLabel; // Return the JLabel to be added to the GUI
	}
	
	public String getFrontCard() {
		return frontCard; // Return the name of the front card
	}

}
