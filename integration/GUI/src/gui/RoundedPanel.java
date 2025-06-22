package gui;

import javax.swing.*;
import java.awt.*;

/**
 * A JPanel that provides a rounded corner effect.
 * This class extends JPanel and overrides the paintComponent method to draw a rounded rectangle.
 * It is useful for creating panels with a visually appealing rounded corner design.
 * @param cornerRadius The radius of the corners for the rounded rectangle.
 * * @returns A JPanel with rounded corners.
 */
public class RoundedPanel extends JPanel {
    private int cornerRadius;

    public RoundedPanel(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        setOpaque(false); // Make the panel non-opaque to allow custom painting
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        g2.dispose();
    }
}
