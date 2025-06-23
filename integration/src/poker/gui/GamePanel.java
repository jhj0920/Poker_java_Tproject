package poker.gui;

import poker.logic.GameManager;
import poker.logic.GameState;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;

/**
 * GamePanel is the main panel for the poker game GUI.
 * It organizes the layout into sections for players, center actions, and game controls.
 * Each section is represented by a dedicated panel class.
 * @returns A JPanel that serves as the main game interface, containing all sections of the poker game.
 */
public class GamePanel extends JPanel {
	private GameManager gameManager;
    private TopSectionPanel topSectionPanel;
    private LeftSectionPanel leftSectionPanel;
    private RightSectionPanel rightSectionPanel;
    private BottomSectionPanel bottomSectionPanel;
    private CenterSectionPanel centerSectionPanel;
    private final PrintWriter out;
    private ChatPanel chatPanel;
    
    private static final Color TABLE_COLOR = new Color(0, 128, 0); // Green color for the poker table
    private static final Dimension FRAME_SIZE = new Dimension(1000, 720);
    private static final Rectangle CHAT_PANEL_BOUNDS = new Rectangle(10, 470, 300, 200); // Position at bottom-left corner
    
    public GamePanel(GameManager gameManager, PrintWriter out) {
        this.gameManager = gameManager;
        this.out = out;
        setBackground(TABLE_COLOR);
        setLayout(new BorderLayout());
        add(setupLayeredPane(gameManager), BorderLayout.CENTER);
	}
	
	/**
	 * Sets up the layered pane that contains the main content and chat panel.
	 * The main content is on the default layer, while the chat panel is on a higher layer.
	 * @returns A JLayeredPane containing the main game content and chat panel.
	 */
    private JLayeredPane setupLayeredPane(GameManager gameManager) {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null); // Use null layout for absolute positioning

        // Add the main content panel to the default layer
        layeredPane.add(setupMainContentPanel(gameManager), JLayeredPane.DEFAULT_LAYER);
        chatPanel = setupChatPanel();
        layeredPane.add(chatPanel, JLayeredPane.PALETTE_LAYER); // Add to a higher layer

        return layeredPane;
    }
    
    /**
	 * Sets up the main content panel that contains all sections of the game.
	 * This includes the top section for player 2 and balance, left section for player 1,
	 * right section for player 3, center section for river and pot, and bottom section for player cards and actions.
	 * @returns A JPanel containing all sections of the poker game.
	 */
    private JPanel setupMainContentPanel(GameManager gameManager) {
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BorderLayout());
        mainContentPanel.setBounds(0, 0, FRAME_SIZE.width, FRAME_SIZE.height);
        mainContentPanel.setBackground(TABLE_COLOR);

		// ---------------- Top Section (Player 2, Balance) -----------------
        topSectionPanel = new TopSectionPanel(gameManager, 1);
        mainContentPanel.add(topSectionPanel, BorderLayout.NORTH);
        
        // ---------------- Player 1 (Left, Bottom-Aligned) -----------------
        leftSectionPanel = new LeftSectionPanel(gameManager, 0);
        mainContentPanel.add(leftSectionPanel, BorderLayout.WEST);
		
		// --------------- Player 3 (Right, Bottom-Aligned) -----------------
        rightSectionPanel = new RightSectionPanel(gameManager, 2);
        mainContentPanel.add(rightSectionPanel, BorderLayout.EAST);
		
		// ---------------- Center Section (River, Pot) ---------------------
        centerSectionPanel = new CenterSectionPanel(gameManager);
        mainContentPanel.add(centerSectionPanel, BorderLayout.CENTER);
		
        // ------------ Bottom Section (Player Cards, Actions) --------------
        bottomSectionPanel = new BottomSectionPanel(gameManager, out, 3);
        mainContentPanel.add(bottomSectionPanel, BorderLayout.SOUTH);

        return mainContentPanel;
    }
    
    /** Updates all visible sections without any additional logic. */
    private void refreshUI() {
        boolean showOpponents = gameManager.getState() == GameState.SHOWDOWN;
        leftSectionPanel.refreshCards(showOpponents);
        rightSectionPanel.refreshCards(showOpponents);
        topSectionPanel.refreshCards(showOpponents);
        bottomSectionPanel.refreshCards();
        leftSectionPanel.refreshBalance();
        rightSectionPanel.refreshBalance();
        topSectionPanel.refreshBalance();
        centerSectionPanel.refreshBalance();
        centerSectionPanel.refreshCommunityCards();
        centerSectionPanel.refreshPot();
    }

    /** Refresh all visible sections. */
    public void refreshAll() {
        refreshUI();
    }
    
    /** Highlight the cards for the given player index. */
    public void highlightTurn(int playerIdx) {
        leftSectionPanel.setHighlighted(playerIdx == 0);
        topSectionPanel.setHighlighted(playerIdx == 1);
        rightSectionPanel.setHighlighted(playerIdx == 2);
        bottomSectionPanel.setHighlighted(playerIdx == 3);
    }

    /** Returns the chat panel for appending messages or adding listeners. */
    public ChatPanel getChatPanel() {
        return chatPanel;
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
    
    /** Hide opponents and community cards for a new round. */
    public void hideOpponents() {
        leftSectionPanel.hideCards();
        rightSectionPanel.hideCards();
        topSectionPanel.hideCards();
        centerSectionPanel.hideCommunityCards();
    }

    /** Hide all cards including the local player's hand. */
    public void hideAllCards() {
        hideOpponents();
        bottomSectionPanel.hideCards();
    }
}
