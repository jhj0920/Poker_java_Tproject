package poker.gui;

import poker.logic.GameManager;
import poker.logic.Player;

import javax.swing.*;
import java.awt.*;
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


        callButton.addActionListener(e -> handleCall());
        foldButton.addActionListener(e -> send("FOLD"));
        checkButton.addActionListener(e -> handleCheck());
        allInButton.addActionListener(e -> handleAllIn());
        raiseButton.addActionListener(e -> showRaiseDialog());
    }

    private void send(String cmd) {
        if (out != null) {
            out.println(cmd);
        }
    }
    
    public void refreshCards() {
        var player = gameManager.getPlayers().get(playerIndex);
        var cards = player.getHand().getCards();
        if (cards.size() >= 2) {
        	playerCardsPanel.updateCards(cards.get(0), cards.get(1));
        }
        playerCardsPanel.setDead(player.isFolded() || player.getChips() <= 0);
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
    
    /** Display an informational dialog explaining why an action cannot be taken. */
    private void showInfo(String message) {
        JOptionPane.showMessageDialog(
                null,
                message,
                "유효하지 않은 액션입니다.",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /** Handle call button, validating chips before sending the command. */
    private void handleCall() {
        Player player = getPlayer();
        int currentBet = gameManager.getPlayers().stream()
                .mapToInt(Player::getCurrentBet)
                .max()
                .orElse(0);
        int toCall = currentBet - player.getCurrentBet();
        if (toCall == 0) {
            showInfo("콜할 대상이 없습니다. 체크하거나 레이즈를 고려하세요.");
            return;
        }
        if (toCall > player.getChips()) {
            showInfo("콜을 하기에는 칩이 부족합니다: $" + toCall + "가 필요합니다.");
            return;
        }
        send("CALL");
    }

    /** Handle check button, ensuring no bet is outstanding. */
    private void handleCheck() {
        Player player = getPlayer();
        int currentBet = gameManager.getPlayers().stream()
                .mapToInt(Player::getCurrentBet)
                .max()
                .orElse(0);
        int toCall = currentBet - player.getCurrentBet();
        if (toCall > 0) {
            showInfo("상대가 베팅한 상태에서는 체크할 수 없습니다");
            return;
        }
        send("CHECK");
    }

    /** Handle all-in button with basic validation. */
    private void handleAllIn() {
        Player player = getPlayer();
        if (player.getChips() <= 0) {
            showInfo("올인을 할 칩이 남아있지 않습니다.");
            return;
        }
        send("BET " + player.getChips());
    }


    private void showRaiseDialog() {
        Player player = getPlayer();
        JDialog dialog = new JDialog((Frame) null, "Raise", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(null);

        // Determine the minimum and maximum amounts allowed for a raise.
        int currentBet = gameManager.getPlayers().stream()
                .mapToInt(Player::getCurrentBet)
                .max()
                .orElse(0);
        int max = player.getCurrentBet() + player.getChips();
        int min = Math.max(1, currentBet * 2);

        // If the player cannot meet the minimum raise, inform them and exit.
        if (max < min) {
            JOptionPane.showMessageDialog(
                    null,
                    "최소 레이즈 금액인 $" + min + "보다 칩이 부족합니다.",
                    "레이즈할 수 없습니다",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }
        
        JLabel label = new JLabel("Amount: " + min, SwingConstants.CENTER);        
        dialog.add(label, BorderLayout.NORTH);
        JSlider slider = new JSlider(min, max, min);
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
