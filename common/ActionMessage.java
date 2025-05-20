package common;

import java.io.Serializable;

public class ActionMessage implements Serializable {
    private final String playerName;
    private final String action; // "CALL", "RAISE", "FOLD"
    private final int amount;    // RAISE일 경우만 사용

    public ActionMessage(String playerName, String action, int amount) {
        this.playerName = playerName;
        this.action = action;
        this.amount = amount;
    }

    public String getPlayerName() { return playerName; }
    public String getAction() { return action; }
    public int getAmount() { return amount; }

    @Override
    public String toString() {
        return playerName + " " + action + " " + (action.equals("RAISE") ? amount : "");
    }
}
