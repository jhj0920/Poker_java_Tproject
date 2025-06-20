package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class CenterSectionPanel extends BaseSectionPanel{
		private static final Dimension POT_PANEL_SIZE = new Dimension(200, 100);
	    private static final Font POT_FONT = new Font("Arial", Font.BOLD, 16);
	    private static final Color POT_COLOR = Color.DARK_GRAY;
	    private static final Color TEXT_COLOR = Color.WHITE;

	public CenterSectionPanel() {
		setBackground(TABLE_COLOR);
		setLayout(new BorderLayout());
		
		JPanel centerContainer = new JPanel();
		centerContainer.setBackground(TABLE_COLOR);
		centerContainer.setLayout(new GridBagLayout()); // Use BoxLayout for vertical stacking


		// Create a panel for the river cards
		
		RiverCardPanel riverCardPanel = new RiverCardPanel(
				Arrays.asList("card_back", "card_back", "card_back", "card_back", "card_back"),
				TABLE_COLOR
		);
		centerContainer.add(
				riverCardPanel.initializeCards(),
				GridBagConstraintsFactory.createConstraints(0, 0, GridBagConstraints.CENTER, 0.0, 0.0, GridBagConstraints.NONE)
		); // Add river cards panel to the center section

		// Create a panel for the pot
		centerContainer.add(
				createPotPanel(),
				GridBagConstraintsFactory.createConstraints(0, 1, GridBagConstraints.CENTER, 0.0, 0.0, GridBagConstraints.NONE)
		); // Center the pot panel

		add(centerContainer, BorderLayout.CENTER); // Add center container to the center section
	}
	
	private JPanel createPotPanel() {
		RoundedPanel potPanel = new RoundedPanel(20);
		potPanel.setLayout(new GridBagLayout());
		potPanel.setPreferredSize(POT_PANEL_SIZE);
		potPanel.setBackground(POT_COLOR);

		JLabel potLabel = new JLabel("Pot: $0"); // Example pot amount, replace with actual game logic
		potLabel.setFont(POT_FONT);
		potLabel.setForeground(TEXT_COLOR);
		potPanel.add(potLabel, GridBagConstraintsFactory.createDefaultConstraints(0, 0));

		return potPanel;
	}
}
