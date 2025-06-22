package gui;

import javax.swing.*;
import java.awt.*;

/**
 * ChatPanel is a custom panel for displaying chat messages and an input field for user input.
 * It extends JLayeredPane to allow for layering of components.
 * The chat area is a JTextArea that displays messages, and the input field is a JTextField for user input.
 * The panel has a semi-transparent background to blend with the game interface.
 */
public class ChatPanel extends JLayeredPane {
    private static final Dimension CHAT_PANEL_SIZE = new Dimension(300, 200);
    private static final Dimension INPUT_FIELD_SIZE = new Dimension(280, 30);

    private JTextArea chatArea;
    private JTextField inputField;

    public ChatPanel() {
        setPreferredSize(CHAT_PANEL_SIZE);
        setLayout(null); // Use null layout for absolute positioning

        add(createChatAreaScrollPane(), JLayeredPane.DEFAULT_LAYER);
        add(createInputField(), JLayeredPane.PALETTE_LAYER);
    }
    
    /**
	 * Creates a scroll pane for the chat area.
	 * The chat area is a JTextArea that displays messages.
	 * The scroll pane allows for scrolling through the chat messages.
	 * @returns A JScrollPane containing the chat area.
	 */
    private JScrollPane createChatAreaScrollPane() {
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setBackground(new Color(0, 0, 0, 100)); // Semi-transparent background
        chatArea.setForeground(Color.WHITE);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setBounds(0, 0, CHAT_PANEL_SIZE.width, CHAT_PANEL_SIZE.height - INPUT_FIELD_SIZE.height);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        return scrollPane;
    }

    /**
     * Creates an input field for the chat panel.
     * The input field is a JTextField where users can type their messages.
     * @return A JTextField for user input.
     */
    private JTextField createInputField() {
        inputField = new JTextField();
        inputField.setBounds(10, CHAT_PANEL_SIZE.height - INPUT_FIELD_SIZE.height, INPUT_FIELD_SIZE.width, INPUT_FIELD_SIZE.height);
        inputField.setBackground(new Color(0, 0, 0, 100)); // Semi-transparent background
        inputField.setForeground(Color.WHITE);
        inputField.setFont(new Font("Arial", Font.PLAIN, 14));

        return inputField;
    }

    public JTextArea getChatArea() {
        return chatArea;
    }

    public JTextField getInputField() {
        return inputField;
    }
}
