package poker.client;
import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * 클라이언트 프로그램
 * 사용자가 방을 생성하거나 참가할 수 있는 기능을 제공
 * 서버와의 통신을 통해 게임 상태를 관리
 */
public class Client {
    private static final String SERVER_IP = "localhost";
    private static final int PORT = 8888;

    public static void main(String[] args) {
        boolean hasJoinedOrCreated = false;

        try (
            Socket socket = new Socket(SERVER_IP, PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);
        ) {
            System.out.println("[서버] " + in.readLine());

            while (true) {
                System.out.print("명령 입력 (예: CREATE, JOIN 1234, 또는 LEAVE): ");
                String command = scanner.nextLine().trim().toUpperCase();

                // CREATE 또는 JOIN으로 시작하는 명령만 제한
                if (hasJoinedOrCreated && (command.startsWith("CREATE") || command.startsWith("JOIN"))) {
                    System.out.println("이미 방에 참가하거나 생성했습니다. 해당 명령은 실행할 수 없습니다.");
                    continue;
                }

                out.println(command);
                String response;
                while ((response = in.readLine()) != null) {
                    System.out.println("[서버] " + response);

                    if (response.startsWith("PARTY_READY")) {
                        System.out.println("게임 시작 대기 완료!");
                        hasJoinedOrCreated = true;
                        break;
                    } else if (response.startsWith("PARTY_CREATED") || response.startsWith("JOINED")) {
                        hasJoinedOrCreated = true;
                        break;
                    } else if (response.startsWith("LEFT")) {
                        hasJoinedOrCreated = false;
                        break;
                    } else if (response.startsWith("ERROR")) {
                        break;
                    }


                }
            }
        } catch (IOException e) {
            System.err.println("서버와 연결할 수 없습니다: " + e.getMessage());
        }
    }
}
