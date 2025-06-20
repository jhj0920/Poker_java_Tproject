package gui;

import javax.swing.*;
import java.awt.*;

/**
 * GamePanel is the main panel for the poker game GUI.
 * It organizes the layout into sections for players, center actions, and game controls.
 * Each section is represented by a dedicated panel class.
 * @returns A JPanel that serves as the main game interface, containing all sections of the poker game.
 */
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
