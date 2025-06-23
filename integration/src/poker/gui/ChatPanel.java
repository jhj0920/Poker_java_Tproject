package poker.gui;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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
    private final List<ActionListener> listeners = new ArrayList<>();

    public ChatPanel() {
        setPreferredSize(CHAT_PANEL_SIZE);
        setLayout(null); // Use null layout for absolute positioning
        setOpaque(false); // keep table visible through the panel

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
        chatArea = new JTextArea() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(0, 0, 0, 100));
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setForeground(Color.WHITE);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 14));
        chatArea.setOpaque(false); // custom background ensures transparency
        ((DefaultCaret) chatArea.getCaret()).setUpdatePolicy(
            DefaultCaret.ALWAYS_UPDATE);

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
        inputField = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(0, 0, 0, 100));
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        inputField.setBounds(10, CHAT_PANEL_SIZE.height - INPUT_FIELD_SIZE.height, INPUT_FIELD_SIZE.width, INPUT_FIELD_SIZE.height);
        inputField.setForeground(Color.BLACK);
        inputField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputField.setOpaque(false); // custom background ensures transparency
        inputField.addActionListener(e -> {
            String text = inputField.getText().trim();
            if (!text.isEmpty()) {
                ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, text);
                for (ActionListener l : listeners) {
                    l.actionPerformed(evt);
                }
                inputField.setText("");
            }
        });

        return inputField;
    }

    public JTextArea getChatArea() {
        return chatArea;
    }

    public JTextField getInputField() {
        return inputField;
    }

    /** Append a new message to the chat area. */
    public void appendMessage(String msg) {
        chatArea.append(msg + "\n");
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    /** Register a listener triggered when the user sends a message. */
    public void addSendListener(ActionListener l) {
        listeners.add(l);
    }
}
