package gui;

import javax.swing.*;
import java.awt.*;

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
