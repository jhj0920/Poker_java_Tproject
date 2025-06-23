package poker.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Simple dialog that shows a victory image with a message.
 * Displayed when a player wins by being the last one standing.
 */
public class VictoryDialog extends JDialog {
    public VictoryDialog(JFrame owner, String message) {
        super(owner, "Victory!", true);
        setLayout(new BorderLayout());

        ImageIcon icon = new ImageIcon(
                VictoryDialog.class.getResource("/poker/gui/img/pokerVictory.jpg"));
        JLabel imageLabel = new JLabel(icon);
        add(imageLabel, BorderLayout.CENTER);

        JLabel msgLabel = new JLabel(message, SwingConstants.CENTER);
        msgLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(msgLabel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(owner);
    }
}