package poker.gui;

import poker.logic.GameManager;
import poker.logic.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

/**
 * Simplified bottom panel used in network play. It displays the user's
 * cards and provides buttons to send actions to the server.
 */
public class BottomSectionPanel extends BaseSectionPanel {
    private static final Dimension BUTTON_SIZE = new Dimension(100, 50);
    
    private final GameManager gameManager;
    private final PrintWriter out;
    private final int playerIndex;
    private PlayerCardPanel playerCardsPanel;
    private JPanel cardContainer;

    public BottomSectionPanel(GameManager gameManager, PrintWriter out, int playerIndex) {
        this.gameManager = gameManager;
        this.out = out;
        this.playerIndex = playerIndex;
        setLayout(new GridBagLayout());

        playerCardsPanel = new PlayerCardPanel("card_back", "card_back", TABLE_COLOR);
        cardContainer = playerCardsPanel.initializeCards();
        add(cardContainer, GridBagConstraintsFactory.createDefaultConstraints(0, 0));

        JPanel actions = new JPanel(new GridBagLayout());
        actions.setBackground(TABLE_COLOR);
        add(actions, GridBagConstraintsFactory.createDefaultConstraints(0, 1));

        JButton callButton = createActionButton("Call");
        JButton raiseButton = createActionButton("Raise");
        JButton foldButton = createActionButton("Fold");
        JButton checkButton = createActionButton("Check");
        JButton allInButton = createActionButton("All In");

        actions.add(callButton, GridBagConstraintsFactory.createDefaultConstraints(0,0));
        actions.add(raiseButton, GridBagConstraintsFactory.createDefaultConstraints(1,0));
        actions.add(foldButton, GridBagConstraintsFactory.createDefaultConstraints(2,0));
        actions.add(checkButton, GridBagConstraintsFactory.createDefaultConstraints(3,0));
        actions.add(allInButton, GridBagConstraintsFactory.createDefaultConstraints(4,0));

        callButton.addActionListener(e -> send("CALL"));
        foldButton.addActionListener(e -> send("FOLD"));
        checkButton.addActionListener(e -> send("BET 0"));
        allInButton.addActionListener(e -> send("BET " + getPlayer().getChips()));
        raiseButton.addActionListener(e -> showRaiseDialog());
    }

    private void send(String cmd) {
        if (out != null) {
            out.println(cmd);
        }
    }
    
    public void refreshCards() {
    	var cards = gameManager.getPlayers().get(playerIndex).getHand().getCards();
        if (cards.size() >= 2) {
        	playerCardsPanel.updateCards(cards.get(0), cards.get(1));
        }
	}
    
    /** Sets this player's cards face down. */
    public void hideCards() {
        playerCardsPanel.setFaceDown();
    }

    /** Highlights this player's cards when it is their turn. */
    public void setHighlighted(boolean on) {
        playerCardsPanel.setHighlighted(on);
    }

    private Player getPlayer() {
        return gameManager.getPlayers().get(playerIndex);
    }

    private JButton createActionButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(BUTTON_SIZE);
        return button;
    }

    private void showRaiseDialog() {
        Player player = getPlayer();
        JDialog dialog = new JDialog((Frame) null, "Raise", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(null);

        int max = player.getChips();
        if (max <= 0) return;

        JLabel label = new JLabel("Amount: 1", SwingConstants.CENTER);
        dialog.add(label, BorderLayout.NORTH);
        JSlider slider = new JSlider(1, max, 1);
        slider.setMajorTickSpacing(Math.max(1, max / 5));
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(e -> label.setText("Amount: " + slider.getValue()));
        dialog.add(slider, BorderLayout.CENTER);

        JButton confirm = new JButton("Confirm");
        confirm.addActionListener(ev -> {
            send("RAISE " + slider.getValue());
            dialog.dispose();
        });
        dialog.add(confirm, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}
