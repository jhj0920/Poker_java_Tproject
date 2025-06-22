package poker.gui;

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
    private static final Dimension FRAME_SIZE = new Dimension(1000, 720);
    private static final Rectangle CHAT_PANEL_BOUNDS = new Rectangle(10, 520, 300, 200); // Position at bottom-left corner
    
	public GamePanel() {
		setBackground(TABLE_COLOR);
        // Add the layered pane to the GamePanel
        setLayout(new BorderLayout());
        add(setupLayeredPane(), BorderLayout.CENTER);
	}
	
	/**
	 * Sets up the layered pane that contains the main content and chat panel.
	 * The main content is on the default layer, while the chat panel is on a higher layer.
	 * @returns A JLayeredPane containing the main game content and chat panel.
	 */
    private JLayeredPane setupLayeredPane() {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null); // Use null layout for absolute positioning

        // Add the main content panel to the default layer
        layeredPane.add(setupMainContentPanel(), JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(setupChatPanel(), JLayeredPane.PALETTE_LAYER); // Add to a higher layer

        return layeredPane;
    }
    
    /**
	 * Sets up the main content panel that contains all sections of the game.
	 * This includes the top section for player 2 and balance, left section for player 1,
	 * right section for player 3, center section for river and pot, and bottom section for player cards and actions.
	 * @returns A JPanel containing all sections of the poker game.
	 */
    private JPanel setupMainContentPanel() {
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BorderLayout());
        mainContentPanel.setBounds(0, 0, FRAME_SIZE.width, FRAME_SIZE.height);
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

        return mainContentPanel;
    }

    /**
	 * Sets up the chat panel that overlays the main content.
	 * The chat panel is positioned at the bottom-left corner of the game panel.
	 * @returns A ChatPanel that allows players to communicate during the game.
	 */
    private ChatPanel setupChatPanel() {
        // ---------------- Chat Panel (Overlay) ----------------------------
        ChatPanel chatPanel = new ChatPanel();
        chatPanel.setBounds(CHAT_PANEL_BOUNDS);
        return chatPanel;
    }
}
