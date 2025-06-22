package gui;

import javax.swing.*;
import java.awt.*;

public class ChatPanel extends JLayeredPane {
    private static final Dimension CHAT_PANEL_SIZE = new Dimension(300, 200);
    private static final Dimension INPUT_FIELD_SIZE = new Dimension(280, 30);

    private JTextArea chatArea;
    private JTextField inputField;

    public ChatPanel() {
        setPreferredSize(CHAT_PANEL_SIZE);
        setLayout(null); // Use null layout for absolute positioning

        // Create the chat area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setBackground(new Color(0, 0, 0, 100)); // Semi-transparent background
        chatArea.setForeground(Color.WHITE);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 14));

        // Add the chat area inside a scroll pane
        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setBounds(0, 0, CHAT_PANEL_SIZE.width, CHAT_PANEL_SIZE.height - INPUT_FIELD_SIZE.height);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // Create the input field
        inputField = new JTextField();
        inputField.setBounds(10, CHAT_PANEL_SIZE.height - INPUT_FIELD_SIZE.height, INPUT_FIELD_SIZE.width, INPUT_FIELD_SIZE.height);
        inputField.setBackground(new Color(0, 0, 0, 100)); // Semi-transparent background
        inputField.setForeground(Color.WHITE);
        inputField.setFont(new Font("Arial", Font.PLAIN, 14));

        // Add components to different layers
        add(scrollPane, JLayeredPane.DEFAULT_LAYER);
        add(inputField, JLayeredPane.PALETTE_LAYER);
    }

    public JTextArea getChatArea() {
        return chatArea;
    }

    public JTextField getInputField() {
        return inputField;
    }
}
