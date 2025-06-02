package client;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private static final String SERVER_IP = "localhost"; 
    private static final int PORT = 8888;         

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(SERVER_IP,PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);
        ) {
            System.out.println("[서버] " + in.readLine());

            while (true) {
                System.out.print("명령 입력 (예: CREATE 또는 JOIN 1234): ");     
                String command = scanner.nextLine();
                out.println(command);
                String response;
                while ((response = in.readLine()) != null) {
                    System.out.println("[서버] " + response);
              
                    if (response.startsWith("PARTY_READY")) {
                        System.out.println("게임 시작 대기 완료!");
                        break;
                    }

                    else if (response.startsWith("PARTY_CREATED") || response.startsWith("JOINED") || response.startsWith("ERROR")) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("서버와 연결할 수 없습니다: " + e.getMessage());
        }
    }
}

