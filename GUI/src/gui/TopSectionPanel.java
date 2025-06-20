package gui;

import javax.swing.*;
import java.awt.*;

public class TopSectionPanel extends JPanel {
    private static final Color TABLE_COLOR = new Color(0, 128, 0); // Green color for the poker table
    private static final Dimension BALANCE_PANEL_SIZE = new Dimension(150, 50);
    private static final Font BALANCE_FONT = new Font("Arial", Font.BOLD, 16);
    private static final Color BALANCE_COLOR = Color.DARK_GRAY;
    private static final Color TEXT_COLOR = Color.WHITE;
    
    public TopSectionPanel() {
        setLayout(new GridBagLayout());
        setBackground(TABLE_COLOR); // Green color for the poker table

        // Add player 2 cards panel
        add(createPlayerCardsPanel(), GridBagConstraintsFactory.createDefaultConstraints(1, 0));

        // Add balance panel
        add(createBalancePanel(), GridBagConstraintsFactory.createConstraints(1, 0, GridBagConstraints.NORTHEAST, 0.0, 0.0, GridBagConstraints.NONE));
        }
    
    private JPanel createPlayerCardsPanel() {
        JPanel player2Cards = new JPanel();
        player2Cards.setBackground(getBackground());

        JPanel p2CardsGroup = new JPanel();
        p2CardsGroup.setBackground(getBackground());
        player2Cards.add(p2CardsGroup);

        Card player2Card1 = new Card("card_back"); // Example card, replace with actual game logic
        p2CardsGroup.add(player2Card1.getCardLabel());
        Card player2Card2 = new Card("card_back"); // Example card, replace with actual game logic
        p2CardsGroup.add(player2Card2.getCardLabel());

        return player2Cards;
    }

    private JPanel createBalancePanel() {
        RoundedPanel balancePanel = new RoundedPanel(20);
        balancePanel.setLayout(new GridBagLayout());
        balancePanel.setPreferredSize(BALANCE_PANEL_SIZE);
        balancePanel.setBackground(BALANCE_COLOR);

        JLabel balanceLabel = new JLabel("Balance: $1000"); // Example balance, replace with actual game logic
        balanceLabel.setFont(BALANCE_FONT);
        balanceLabel.setForeground(TEXT_COLOR);
        balancePanel.add(balanceLabel, GridBagConstraintsFactory.createDefaultConstraints(0, 0));

        return balancePanel;
    }
}
