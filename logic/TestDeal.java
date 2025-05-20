package logic;

import java.util.Arrays;

public class TestDeal {
    public static void main(String[] args) {
        GameState state = new GameState(Arrays.asList("Alice", "Bob", "Charlie", "David"));
        state.printState();
    }
}
