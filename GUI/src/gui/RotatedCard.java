package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
// Child class of Card that represents a card with a rotation angle

/* * Represents a rotated card in the GUI.
 * This class extends Card to provide functionality for displaying a card with a specified rotation angle.
 * It overrides the paintComponent method to draw the card image at the specified angle.
 * @param frontCard The identifier for the front image of the card (e.g., "ace_of_hearts").
 * @param rotationAngle The angle in degrees to rotate the card image.
 * @returns A JLabel that displays the rotated card image.
 */
public class RotatedCard extends Card {
		private final int rotationAngle;
		private final BufferedImage cardImage; // Store the card image for rotation

	public RotatedCard(String frontCard, int rotationAngle) {
		super(frontCard);
		this.cardImage = (BufferedImage) cardLabel.getIcon();
		this.rotationAngle = rotationAngle;
		// Set the icon to the rotated card image
		setPreferredSize(new Dimension(cardImage.getHeight(), cardImage.getWidth())); // Adjust for rotation
	}
	
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        int x = getWidth() / 2;
        int y = getHeight() / 2;

        // Rotate the image
        g2d.rotate(Math.toRadians(rotationAngle), x, y);
        g2d.drawImage(cardImage, (getWidth() - cardImage.getWidth()) / 2, (getHeight() - cardImage.getHeight()) / 2, null);
        g2d.dispose();
        
        // Set the icon to the rotated image
        cardLabel.setIcon(new ImageIcon(cardImage.getScaledInstance(cardImage.getWidth(), cardImage.getHeight(), Image.SCALE_SMOOTH)));
    }

	// flip method is inherited from Card class, so no need to override it here
	
	@Override
	public JLabel getCardLabel() {
		return cardLabel; // Return the JLabel to be added to the GUI
	}
	
	@Override
	public String getFrontCard() {
		return frontCard; // Return the name of the front card
	}
}
