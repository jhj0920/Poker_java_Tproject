package poker.gui;

import poker.server.MatchmakingServer;

/**
 * PokerLauncher starts the matchmaking server and opens the lobby GUI so
 * players can connect and start games without running separate processes.
 */
public class PokerLauncher {
    /**
     * Launches the lobby GUI and, unless {@code --client-only} is specified,
     * also starts a local matchmaking server. The lobby can connect to a
     * remote server by providing a host and port.
     *
     * Usage: {@code PokerLauncher [--client-only] [--host <addr>] [--port <num>]}
     */
    public static void main(String[] args) {
        String host = "localhost";
        int port = 8888;
        boolean clientOnly = false;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--client-only":
                    clientOnly = true;
                    break;
                case "--host":
                case "-h":
                    if (i + 1 < args.length) {
                        host = args[++i];
                    } else {
                        System.err.println("Missing value for --host");
                        return;
                    }
                    break;
                case "--port":
                case "-p":
                    if (i + 1 < args.length) {
                        try {
                            port = Integer.parseInt(args[++i]);
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid port: " + args[i]);
                            return;
                        }
                    } else {
                        System.err.println("Missing value for --port");
                        return;
                    }
                    break;
                default:
                    System.err.println("Unknown option: " + args[i]);
                    System.err.println("Usage: PokerLauncher [--client-only] [--host <addr>] [--port <num>]");
                    return;
            }
        }

        if (!clientOnly) {
            Thread serverThread = new Thread(() -> MatchmakingServer.main(new String[0]));
            serverThread.setDaemon(true);
            serverThread.start();
        }

        LobbyFrame.main(new String[] { host, Integer.toString(port) });
    }
}