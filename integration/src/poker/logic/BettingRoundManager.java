package poker.logic;

// 한 라운드의 베팅을 관리하는 유틸리티 클래스

/**
 * BettingRoundManager.java
 * This class manages a betting round in a poker game.
 * The class handles player actions such as call, raise, fold, check, and all-in.
 * It keeps track of the current bet, the last raise amount, and the chips in the pot.
 * 
 */
import java.util.*;

public class BettingRoundManager {
    // ---------------------- GUI Integration ----------------------
    private GameManager gameManager;
    private int currentBet;
    private int lastRaiseAmount;
    private final Map<Player, Integer> bets = new HashMap<>();

    /**
     * Constructs a manager used by the GUI. The static runBettingRound method
     * is still available for the console version of the game but the GUI
     * interacts with these instance methods to update the game state.
     */
    public BettingRoundManager(GameManager gameManager) {
        this.gameManager = gameManager;
        reset();
    }

    /** Resets internal state for a new round. */
    public void reset() {
        currentBet = 0;
        lastRaiseAmount = 0;
        bets.clear();
        for (Player p : gameManager.getPlayers()) {
            bets.put(p, 0);
        }
    }

    public int getCurrentBet() { return currentBet; }

    /**
     * Returns the minimum additional amount required to perform a raise.
     * This is based on the last raise that occurred or twice the current bet
     * if no raises have been made yet.
     */
    public int getMinimumRaiseAmount() {
        int minRaiseTo = lastRaiseAmount == 0 ? currentBet * 2 : currentBet + lastRaiseAmount;
        return Math.max(0, minRaiseTo - currentBet);
    }

    /** Amount the player needs to call to stay in the hand. */
    public int getAmountToCall(Player player) {
        return Math.max(0, currentBet - bets.getOrDefault(player, 0));
    }

    /**
     * Attempts to call the current bet for the given player.
     *
     * @return null if the action succeeded, otherwise a message describing why
     *         the call is not allowed.
     */
    public String call(Player player) {
        int toCall = getAmountToCall(player);
        if (toCall == 0) {
            return "콜할 금액이 없습니다. 체크 또는 레이즈만 가능합니다.";
        }
        if (toCall > player.getChips()) {
            return "보유한 칩보다 많은 금액을 베팅할 수 없습니다.";
        }

        gameManager.getPot().addChips(player, toCall);
        player.setChips(player.getChips() - toCall);
        bets.put(player, bets.get(player) + toCall);
        player.setCurrentBet(bets.get(player));
        return null;
    }

    /**
     * Attempts to raise to the specified amount for the player.
     *
     * @param raiseTo total amount the player wishes to have bet after raising
     * @return null if successful, otherwise a reason the raise cannot be made
     */
    public String raise(Player player, int raiseTo) {
        int minRaise = lastRaiseAmount == 0 ? currentBet * 2 : currentBet + lastRaiseAmount;
        int raiseAmount = raiseTo - bets.get(player);

        if (raiseTo < minRaise) {
            return "최소 레이즈 금액은 이전 베팅보다 최소 2배 이상이어야 합니다.";
        }
        if (raiseAmount > player.getChips()) {
            return "보유한 칩보다 많은 금액을 베팅할 수 없습니다.";
        }

        int previousCurrentBet = currentBet;
        currentBet = raiseTo;
        lastRaiseAmount = raiseTo - previousCurrentBet;
        gameManager.getPot().addChips(player, raiseAmount);
        player.setChips(player.getChips() - raiseAmount);
        bets.put(player, raiseTo);
        player.setCurrentBet(raiseTo);
        return null;
    }

    /**
     * Folds the player from the current hand.
     *
     * @return always null, provided for API symmetry
     */
    public String fold(Player player) {
        player.fold();
        return null;
    }

    /**
     * Attempts to check.
     *
     * @return null if successful or an error message if a call is required
     */
    public String check(Player player) {
        int toCall = getAmountToCall(player);
        if (toCall == 0) {
            return null;
        }
        return "체크할 수 없습니다. 콜 또는 폴드하세요.";
    }

    /**
     * Attempts to go all-in with the player's remaining chips.
     *
     * @return null if successful or an error message if the player has no chips
     */
    public String allIn(Player player) {
        int allInAmount = player.getChips();
        if (allInAmount <= 0) {
            return "올인할 수 있는 칩이 없습니다.";
        }
        gameManager.getPot().addChips(player, allInAmount);
        int totalBetAllIn = bets.get(player) + allInAmount;
        player.setChips(0);
        bets.put(player, totalBetAllIn);
        if (totalBetAllIn > currentBet) {
            lastRaiseAmount = totalBetAllIn - currentBet;
            currentBet = totalBetAllIn;
        }
        player.setCurrentBet(totalBetAllIn);
        return null;
    }



    // ---------------------- Console Version ---------------------- (get rid of this later)
	/**
	 * Runs a betting round for the given players.
	 * 
	 * @param players The list of players participating in the betting round.
	 * @param pot The pot where chips are collected during the betting round.
	 * @param scanner Scanner for user input.
	 */
    public static void runBettingRound(List<Player> players, Pot pot, Scanner scanner) {
        int currentBet = 0;
        int lastRaiseAmount = 0;
        boolean bettingFinished = false;
        boolean allInOccurred = false;

        Map<Player, Integer> bets = new HashMap<>();
        for (Player p : players) {
            bets.put(p, 0);
        }

        while (!bettingFinished) {
            bettingFinished = true;

            for (Player player : players) {
                if (player.isFolded()) continue;
                if (player.getChips() == 0) continue;

                int toCall = currentBet - bets.get(player);
                boolean validInput = false;

                while (!validInput) {
                    System.out.println("\n" + player.getName() + "의 차례입니다. 현재 칩: $" + player.getChips());
                    System.out.println("현재 베팅 금액: $" + currentBet + " | 당신이 콜하려면: $" + toCall);
                    System.out.print("[1] 콜  [2] 레이즈  [3] 폴드  [4] 체크  [5] 올인 > ");

                    int choice = scanner.nextInt();

                    switch (choice) {
                        case 1 -> {
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
                        case 2 -> {
                            int minRaise = lastRaiseAmount == 0 ? currentBet * 2 : currentBet + lastRaiseAmount;
                            int raiseTo = 0;
                            System.out.print("얼마까지 베팅하시겠습니까? (최소 $" + minRaise + ") > ");
                            raiseTo = scanner.nextInt();
                            int raiseAmount = raiseTo - bets.get(player);

                            if (raiseTo < minRaise) {
                                System.out.println("최소 레이즈 금액은 이전 베팅보다 최소 2배 이상이어야 합니다. 다시 입력해주세요.");
                                break;
                            }
                            if (raiseAmount > player.getChips()) {
                                System.out.println("보유한 칩보다 많은 금액을 베팅할 수 없습니다. 다시 입력해주세요.");
                                break;
                            }

                            int previousCurrentBet = currentBet;
                            currentBet = raiseTo;
                            lastRaiseAmount = raiseTo - previousCurrentBet;
                            pot.addChips(player, raiseAmount);
                            player.setChips(player.getChips() - raiseAmount);
                            bets.put(player, raiseTo);
                            bettingFinished = false;
                            System.out.println(player.getName() + "이(가) $" + raiseAmount + " 레이즈하였습니다.");
                            validInput = true;
                        }
                        case 3 -> {
                            player.fold();
                            System.out.println(player.getName() + "이(가) 폴드하였습니다.");
                            validInput = true;
                        }
                        case 4 -> {
                            if (toCall == 0) {
                                System.out.println(player.getName() + "이(가) 체크하였습니다.");
                                validInput = true;
                            } else {
                                System.out.println("체크할 수 없습니다. 콜 또는 폴드하세요.");
                            }
                        }
                        case 5 -> {
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
                                lastRaiseAmount = totalBetAllIn - currentBet;
                                currentBet = totalBetAllIn;
                                bettingFinished = false;
                            }
                            System.out.println(player.getName() + "이(가) $" + allInAmount + " 올인하였습니다.");
                            validInput = true;
                            allInOccurred = true;
                        }
                        default -> System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
                    }
                }
            }

            if (allInOccurred) break;
        }
    }
}
