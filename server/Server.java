package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static List<ObjectOutputStream> clientOutputs = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println("서버 시작됨 (포트 9999)");

        while (true) {
            Socket client = serverSocket.accept();
            System.out.println("클라이언트 접속: " + client);
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            clientOutputs.add(out);

            Thread thread = new Thread(() -> handleClient(client, out));
            thread.start();
        }
    }

    private static void handleClient(Socket client, ObjectOutputStream out) {
        try (ObjectInputStream in = new ObjectInputStream(client.getInputStream())) {
            while (true) {
                Object msg = in.readObject();
                System.out.println("받은 메시지: " + msg);
                for (ObjectOutputStream o : clientOutputs) {
                    o.writeObject(msg);
                    o.flush();
                }
            }
        } catch (Exception e) {
            System.out.println("클라이언트 연결 종료: " + client);
            clientOutputs.remove(out);
        }
    }
}
