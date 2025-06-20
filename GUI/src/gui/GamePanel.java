package gui;

import javax.swing.*;
import java.awt.*;
// Need to refactor GamePanel to organize the layout and components for a Texas Hold'em Poker game GUI.

public class GamePanel extends JPanel {
    private static final Color TABLE_COLOR = new Color(0, 128, 0); // Green color for the poker table
    
	public GamePanel() {
		setBackground(TABLE_COLOR);
		setLayout(new BorderLayout());
		
		// ---------------- Top Section (Player 2, Balance) -----------------
        TopSectionPanel topSectionPanel = new TopSectionPanel();
        add(topSectionPanel, BorderLayout.NORTH);
        
        // ---------------- Player 1 (Left, Bottom-Aligned) -----------------
        LeftSectionPanel leftSectionPanel = new LeftSectionPanel();
        add(leftSectionPanel, BorderLayout.WEST);
		
		// --------------- Player 3 (Right, Bottom-Aligned) -----------------
        RightSectionPanel rightSectionPanel = new RightSectionPanel();
        add(rightSectionPanel, BorderLayout.EAST);
		
		// ---------------- Center Section (River, Pot) ---------------------
        CenterSectionPanel centerSectionPanel = new CenterSectionPanel();
        add(centerSectionPanel, BorderLayout.CENTER);
		
        // ------------ Bottom Section (Player Cards, Actions) --------------
        BottomSectionPanel bottomSectionPanel = new BottomSectionPanel();
        add(bottomSectionPanel, BorderLayout.SOUTH);
	}
}
