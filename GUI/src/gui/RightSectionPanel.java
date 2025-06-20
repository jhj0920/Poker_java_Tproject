package gui;

import javax.swing.*;
import java.awt.*;

/**
 * A panel representing the right section of the game GUI.
 * This class extends BaseSectionPanel to provide a specific implementation for displaying player cards.
 * It initializes a player card panel with two cards and adds it to the right side of the section.
 * * @returns A JPanel that displays player cards in the right section of the game GUI.
 */
public class RightSectionPanel extends BaseSectionPanel {
	private static final Dimension PANEL_SIZE = new Dimension(170, 0); // Example size, adjust as needed

	public RightSectionPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel player2CardsContainer = new JPanel();
		player2CardsContainer.setLayout(new BoxLayout(player2CardsContainer, BoxLayout.Y_AXIS));
		player2CardsContainer.setBackground(TABLE_COLOR);
		player2CardsContainer.add(Box.createRigidArea(new Dimension(0, 120))); // Adds 120px vertical space

		PlayerCardPanel playerCardPanel = new PlayerCardPanel("card_back", "card_back", TABLE_COLOR);
		JPanel player2Cards = playerCardPanel.initializeCards();
		player2Cards.setPreferredSize(PANEL_SIZE);
		player2CardsContainer.add(player2Cards);

		add(player2CardsContainer, BorderLayout.EAST);
	}

}
