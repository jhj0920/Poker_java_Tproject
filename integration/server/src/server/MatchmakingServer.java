package server;
import java.io.*;
import java.net.*;

public class MatchmakingServer {
    private static final int PORT = 8888;
    private static final PartyManager partyManager = new PartyManager();

    public static void main(String[] args) {
    	// 서버 시작
        System.out.println("Matchmaking Server started on port " + PORT);
        // try allows the server to handle exceptions and close resources properly
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
            	// 클라이언트 연결을 기다림
                Socket clientSocket = serverSocket.accept();
                // 클라이언트가 연결되면 새로운 스레드로 클라이언트 핸들러를 시작
                System.out.println("클라이언트 접속: " + clientSocket);
                new Thread(new ClientHandler(clientSocket, partyManager)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
