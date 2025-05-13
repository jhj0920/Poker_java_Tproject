package client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 9999);
        System.out.println("서버에 연결됨");

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        // 메시지 수신 스레드
        new Thread(() -> {
            try {
                while (true) {
                    Object msg = in.readObject();
                    System.out.println("[받음] " + msg);
                }
            } catch (Exception e) {
                System.out.println("서버와 연결 종료");
            }
        }).start();

        // 메시지 전송 루프
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("보낼 메시지: ");
            String line = sc.nextLine();
            out.writeObject(line);
            out.flush();
        }
    }
}
