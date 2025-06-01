// 한 라운드의 베팅을 관리하는 유틸리티 클래스

import java.util.*;

public class BettingRoundManager {

    /**
     * 한 베팅 라운드를 진행한다. 각 플레이어는 순서대로 액션을 선택하고,
     * 현재 베팅 금액에 맞춰 콜, 레이즈, 폴드, 체크, 올인을 할 수 있다.
     * @param players 참여 중인 플레이어 목록
     * @param pot 현재 팟 객체
     * @param scanner 사용자 입력용 Scanner
     */
    public static void runBettingRound(List<Player> players, Pot pot, Scanner scanner) {
        int currentBet = 0;
        boolean bettingFinished = false;

        Map<Player, Integer> bets = new HashMap<>();
        for (Player p : players) {
            bets.put(p, 0);
        }

        while (!bettingFinished) {
            bettingFinished = true;

            for (Player player : players) {
                if (player.isFolded()) continue;

                int toCall = currentBet - bets.get(player);
                boolean validInput = false;

                while (!validInput) {
                    System.out.println("\n" + player.getName() + "의 차례입니다. 현재 칩: $" + player.getChips());
                    System.out.println("현재 베팅 금액: $" + currentBet + " | 당신이 콜하려면: $" + toCall);
                    System.out.print("[1] 콜  [2] 레이즈  [3] 폴드  [4] 체크  [5] 올인 > ");

                    int choice = scanner.nextInt();

                    switch (choice) {
                        case 1 -> { // 콜
                            if (toCall == 0) {
                                System.out.println("콜할 금액이 없습니다. 체크 또는 레이즈만 가능합니다.");
                            } else {
                                pot.addChips(player, toCall);
                                player.setChips(player.getChips() - toCall);
                                bets.put(player, currentBet);
                                System.out.println(player.getName() + "이(가) 콜하였습니다.");
                                validInput = true;
                            }
                        }
                        case 2 -> { // 레이즈
                            System.out.print("얼마를 레이즈 하시겠습니까? > ");
                            int raise = scanner.nextInt();
                            int totalBet = toCall + raise;

                            if (totalBet > player.getChips()) {
                                System.out.println("보유한 칩보다 많은 금액을 베팅할 수 없습니다. 다시 입력해주세요.");
                                break;
                            }

                            currentBet += raise;
                            pot.addChips(player, totalBet);
                            player.setChips(player.getChips() - totalBet);
                            bets.put(player, currentBet);
                            bettingFinished = false;
                            System.out.println(player.getName() + "이(가) $" + raise + " 레이즈하였습니다.");
                            validInput = true;
                        }
                        case 3 -> { // 폴드
                            player.fold();
                            System.out.println(player.getName() + "이(가) 폴드하였습니다.");
                            validInput = true;
                        }
                        case 4 -> { // 체크
                            if (toCall == 0) {
                                System.out.println(player.getName() + "이(가) 체크하였습니다.");
                                validInput = true;
                            } else {
                                System.out.println("체크할 수 없습니다. 콜 또는 폴드하세요.");
                            }
                        }
                        case 5 -> { // 올인
                            int allInAmount = player.getChips();
                            if (allInAmount <= 0) {
                                System.out.println("올인할 수 있는 칩이 없습니다.");
                                break;
                            }
                            pot.addChips(player, allInAmount);
                            int totalBetAllIn = bets.get(player) + allInAmount;
                            player.setChips(0);
                            bets.put(player, totalBetAllIn);
                            if (totalBetAllIn > currentBet) {
                                currentBet = totalBetAllIn;
                                bettingFinished = false;
                            }
                            System.out.println(player.getName() + "이(가) $" + allInAmount + " 올인하였습니다.");
                            validInput = true;
                        }
                        default -> System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
                    }
                }
            }
        }
    }
}
