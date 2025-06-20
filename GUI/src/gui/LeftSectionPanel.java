package gui;

import javax.swing.*;
import java.awt.*;

public class LeftSectionPanel extends BaseSectionPanel{
	private static final Dimension PANEL_SIZE = new Dimension(170, 0); // Example size, adjust as needed
	
	public LeftSectionPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
        JPanel player1CardsContainer = new JPanel();
        player1CardsContainer.setLayout(new BoxLayout(player1CardsContainer, BoxLayout.Y_AXIS));
        player1CardsContainer.setBackground(TABLE_COLOR);
        player1CardsContainer.add(Box.createRigidArea(new Dimension(0, 120))); // Adds 120px vertical space

        PlayerCardPanel playerCardPanel = new PlayerCardPanel("card_back", "card_back", TABLE_COLOR);
        JPanel player1Cards = playerCardPanel.initializeCards();
        player1Cards.setPreferredSize(PANEL_SIZE);
        player1CardsContainer.add(player1Cards);
		
		add(player1CardsContainer, BorderLayout.WEST);
	}

}
