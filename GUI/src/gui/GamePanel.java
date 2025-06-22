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
		
        // Create a layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1000, 750));
        layeredPane.setLayout(null); // Use null layout for absolute positioning

        // ---------------- Main Content Panel ----------------------------
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BorderLayout());
        mainContentPanel.setBounds(0, 0, 1000, 720); // Full size of the frame
        mainContentPanel.setBackground(TABLE_COLOR);
		
		// ---------------- Top Section (Player 2, Balance) -----------------
        TopSectionPanel topSectionPanel = new TopSectionPanel();
        mainContentPanel.add(topSectionPanel, BorderLayout.NORTH);
        
        // ---------------- Player 1 (Left, Bottom-Aligned) -----------------
        LeftSectionPanel leftSectionPanel = new LeftSectionPanel();
        mainContentPanel.add(leftSectionPanel, BorderLayout.WEST);
		
		// --------------- Player 3 (Right, Bottom-Aligned) -----------------
        RightSectionPanel rightSectionPanel = new RightSectionPanel();
        mainContentPanel.add(rightSectionPanel, BorderLayout.EAST);
		
		// ---------------- Center Section (River, Pot) ---------------------
        CenterSectionPanel centerSectionPanel = new CenterSectionPanel();
        mainContentPanel.add(centerSectionPanel, BorderLayout.CENTER);
		
        // ------------ Bottom Section (Player Cards, Actions) --------------
        BottomSectionPanel bottomSectionPanel = new BottomSectionPanel();
        mainContentPanel.add(bottomSectionPanel, BorderLayout.SOUTH);
        
        // Add the main content panel to the default layer
        layeredPane.add(mainContentPanel, JLayeredPane.DEFAULT_LAYER);
        
        // ---------------- Chat Panel (Overlay) ----------------------------
        ChatPanel chatPanel = new ChatPanel();
        chatPanel.setBounds(10, 520, 300, 200); // Position at bottom-left corner
        layeredPane.add(chatPanel, JLayeredPane.PALETTE_LAYER); // Add to a higher layer
        
        // Add the layered pane to the GamePanel
        setLayout(new BorderLayout());
        add(layeredPane, BorderLayout.CENTER);
        
	}
}
