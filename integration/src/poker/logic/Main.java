package poker.logic;

// 포커 게임 메인 실행 클래스
import java.util.*;

/**
 * Main.java
 * This class serves as the entry point for the poker game, initializing players,
 * managing game phases, and handling user input for betting rounds.
 * It orchestrates the flow of the game from pre-flop to showdown.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. 플레이어 생성 및 초기화 ($10,000 지급)
        List<Player> players = new ArrayList<>();
        players.add(new Player("철수", 10000));
        players.add(new Player("영희", 10000));
        players.add(new Player("민수", 10000));
        players.add(new Player("지은", 10000));

        GameManager gameManager = new GameManager(players);
        gameManager.startNewRound();

        // 2. 프리플랍 - 패 공개
        System.out.println("\n[프리플랍] 플레이어들의 패:");
        for (Player p : players) {
            if (!p.isFolded()) {
                System.out.println(p.getName() + "의 핸드:");
                System.out.print(p.getHand());
            }
        }

        // 3. 프리플랍 베팅 라운드
        BettingRoundManager.runBettingRound(players, gameManager.getPot(), scanner);

        // 4. FLOP (커뮤니티 카드 3장 공개)
        gameManager.nextPhase();
        printCommunityCards(gameManager);
        BettingRoundManager.runBettingRound(players, gameManager.getPot(), scanner);

        // 5. TURN (1장 추가)
        gameManager.nextPhase();
        printCommunityCards(gameManager);
        BettingRoundManager.runBettingRound(players, gameManager.getPot(), scanner);

        // 6. RIVER (1장 추가)
        gameManager.nextPhase();
        printCommunityCards(gameManager);
        BettingRoundManager.runBettingRound(players, gameManager.getPot(), scanner);

        // 7. 쇼다운
        gameManager.nextPhase();
        System.out.println("\n[쇼다운] 남아있는 플레이어의 핸드:");
        for (Player p : players) {
            if (!p.isFolded()) {
                System.out.println(p.getName() + "의 핸드:");
                System.out.print(p.getHand());
            }
        }

        System.out.println("\n커뮤니티 카드:");
        for (Card c : gameManager.getCommunityCards()) {
            System.out.println(c);
        }

        // 8. 승자 판단 및 팟 분배
        HandEvaluator.determineWinner(players, gameManager.getCommunityCards(), gameManager.getPot());

        // 9. 최종 칩 현황
        System.out.println("\n[최종 칩 현황]");
        for (Player p : players) {
            System.out.println(p.getName() + ": $" + p.getChips());
        }

        scanner.close();
    }

    /**
	 * 커뮤니티 카드를 출력하는 헬퍼 메소드
	 * @param gameManager current game manager instance
	 */
    private static void printCommunityCards(GameManager gameManager) {
        System.out.println("\n[" + gameManager.getState() + "] 커뮤니티 카드:");
        for (Card c : gameManager.getCommunityCards()) {
            System.out.println(c);
        }
    }
}
