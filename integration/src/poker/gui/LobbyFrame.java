package poker.gui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

/**
 * LobbyFrame provides a simple lobby interface to connect to the matchmaking
 * server. Players can create or join a party using a key and the party leader
 * can start the game once more than two players are connected.
 */
public class LobbyFrame extends JFrame {
    private JTextArea logArea;
    private JTextField partyField;
    private JButton createButton;
    private JButton joinButton;
    private JButton startButton;
    private JButton leaveButton;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    
    private GameGUI gameGui;
    private volatile boolean inGame = false;

    private boolean isLeader = false;
    private int playerCount = 1;

    public LobbyFrame(String serverIp, int serverPort) {
        setTitle("Poker Lobby");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Use the poker logo as a background image if available
        URL logoUrl = LobbyFrame.class.getResource("/poker/gui/img/pokerLogo.jpg");
        Container content = getContentPane();
        if (logoUrl != null) {
            ImageIcon logoIcon = new ImageIcon(logoUrl);
            JLabel background = new JLabel(logoIcon);
            setContentPane(background);
            background.setLayout(new BorderLayout());
            content = background;
        }
        content.setLayout(new BorderLayout());

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setOpaque(false);
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setOpaque(false);
        logScroll.getViewport().setOpaque(false);
        add(logScroll, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        partyField = new JTextField(10);
        createButton = new JButton("Create Party");
        joinButton = new JButton("Join Party");
        startButton = new JButton("Start Game");
        leaveButton = new JButton("Leave Party");
        startButton.setEnabled(false);

        bottomPanel.add(new JLabel("Party ID:"));
        bottomPanel.add(partyField);
        bottomPanel.add(createButton);
        bottomPanel.add(joinButton);
        bottomPanel.add(startButton);
        bottomPanel.add(leaveButton);
        add(bottomPanel, BorderLayout.SOUTH);

        createButton.addActionListener(e -> sendCommand("CREATE"));
        joinButton.addActionListener(e -> sendCommand("JOIN " + partyField.getText().trim()));
        startButton.addActionListener(e -> sendCommand("START"));
        leaveButton.addActionListener(e -> sendCommand("LEAVE"));

        connectToServer(serverIp, serverPort);
        setVisible(true);
    }

    private void connectToServer(String serverIp, int serverPort) {
        try {
            socket = new Socket(serverIp, serverPort);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            logArea.append("Connected to server.\n");

            new Thread(() -> {
                try {
                    String line;
                    while ((line = in.readLine()) != null) {
                        if (inGame && gameGui != null) {
                            gameGui.processServerMessage(line);
                        } else {
                            handleServerMessage(line);
                        }
                    }
                } catch (IOException e) {
                    logArea.append("Disconnected from server.\n");
                }
            }).start();
        } catch (IOException e) {
            logArea.append("Failed to connect: " + e.getMessage() + "\n");
        }
    }

    private void handleServerMessage(String line) {
        logArea.append("Server: " + line + "\n");
        if (line.startsWith("PARTY_CREATED")) {
            isLeader = true;
            String id = line.split(" ")[1];
            partyField.setText(id);
            playerCount = 1;
            updateStartButton();
        } else if (line.startsWith("JOINED")) {
            isLeader = false;
            playerCount = 0; // will be updated by PLAYER_COUNT message
            updateStartButton();
        } else if (line.startsWith("PLAYER_COUNT")) {
            playerCount = Integer.parseInt(line.split(" ")[1]);
            updateStartButton();
        } else if (line.startsWith("GAME_START")) {
            try {
                SwingUtilities.invokeAndWait(() -> {
                    dispose();
                    gameGui = new GameGUI(out, playerCount);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            inGame = true;
        } else if (line.startsWith("LEFT")) {
            isLeader = false;
            playerCount = 1;
            updateStartButton();
        }
    }

    private void updateStartButton() {
        startButton.setEnabled(isLeader && playerCount >= 2);
    }

    private void sendCommand(String command) {
        if (out != null) {
            out.println(command);
            logArea.append("Sent: " + command + "\n");
        }
    }

    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = 8888;
        if (args.length > 1) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid port: " + args[1]);
                return;
            }
        }
        final String finalHost = host;
        final int finalPort = port;
        SwingUtilities.invokeLater(() -> new LobbyFrame(finalHost, finalPort));
    }
}