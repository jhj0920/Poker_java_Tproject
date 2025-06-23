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
    private Runnable updateCallback;

    /**
     * Constructs a manager used by the GUI. The static runBettingRound method
     * is still available for the console version of the game but the GUI
     * interacts with these instance methods to update the game state.
     */
    public BettingRoundManager(GameManager gameManager) {
        this.gameManager = gameManager;
        reset();
    }
    
    /** Sets a callback invoked whenever the betting state updates. */
    public void setUpdateCallback(Runnable updateCallback) {
        this.updateCallback = updateCallback;
    }

    private void notifyUpdate() {
        if (updateCallback != null) {
            updateCallback.run();
        }
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
        notifyUpdate();
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
        notifyUpdate();
        return null;
    }

    /**
     * Folds the player from the current hand.
     *
     * @return always null, provided for API symmetry
     */
    public String fold(Player player) {
        player.fold();
        notifyUpdate();
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
        	notifyUpdate();
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
        notifyUpdate();
        return null;
    }
}
