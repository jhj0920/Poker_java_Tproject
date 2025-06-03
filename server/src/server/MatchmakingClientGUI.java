package server;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class MatchmakingClientGUI extends JFrame {

	// logArea is a JTextArea to display messages from the server
    private JTextArea logArea;
    private JTextField partyInput;
    private JButton createButton, joinButton;
    
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public MatchmakingClientGUI(String serverIp, int serverPort) {
        setTitle("Matchmaking Client");
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        logArea = new JTextArea();
        logArea.setEditable(false);
        add(new JScrollPane(logArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        // partyInput is a JTextField for entering the party ID
        partyInput = new JTextField(10);
        createButton = new JButton("Create Party");
        joinButton = new JButton("Join Party");

        bottomPanel.add(new JLabel("Party ID:"));
        bottomPanel.add(partyInput);
        bottomPanel.add(createButton);
        bottomPanel.add(joinButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // Button listeners
        createButton.addActionListener(e -> sendCommand("CREATE"));
        joinButton.addActionListener(e -> sendCommand("JOIN " + partyInput.getText().trim()));

        connectToServer(serverIp, serverPort);
        setVisible(true);
    }

    // connectToServer establishes a connection to the matchmaking server
    private void connectToServer(String serverIp, int serverPort) {
        try {
            socket = new Socket(serverIp, serverPort);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // BufferedReader reads text from the input stream
            out = new PrintWriter(socket.getOutputStream(), true); // true enables auto-flushing of the PrintWriter
            // auto-flushing means that the PrintWriter will automatically flush its output buffer whenever a newline character is written
            // Append a message to the logArea indicating successful connection
            logArea.append("Connected to server.\n");

            // Start a thread to listen to server messages
            new Thread(() -> {
                try {
                	// Continuously read messages from the server
                    String line;
                    while ((line = in.readLine()) != null) {
                        logArea.append("Server: " + line + "\n");
                    }
                } catch (IOException e) {
                    logArea.append("Disconnected from server.\n");
                }
            }).start(); // Start a new thread to listen for messages from the server

        } catch (IOException e) {
            logArea.append("Failed to connect: " + e.getMessage() + "\n");
        }
    }

    // sendCommand sends a command to the server and appends the command to the logArea
    private void sendCommand(String command) {
        if (out != null) {
            out.println(command);
            logArea.append("Sent: " + command + "\n");
        } else {
            logArea.append("Not connected to server.\n");
        }
    }

    public static void main(String[] args) {
        String serverIp = "localhost"; // Replace with the server's IP address in the future
        int serverPort = 8888;

        SwingUtilities.invokeLater(() -> new MatchmakingClientGUI(serverIp, serverPort));
    }
}