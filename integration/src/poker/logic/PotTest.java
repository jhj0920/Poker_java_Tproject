package poker.logic;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import poker.logic.Pot;
import poker.logic.Player;

/**
 * PotTest.java
 * This class contains unit tests for the Pot class, ensuring that the pot calculations
 * for small and big pots are correct based on player contributions.
 */
class PotTest {

    private Pot pot;
    private Player player1;
    private Player player2;
    private Player player3;

    @BeforeEach
    void setUp() {
        pot = new Pot();
        player1 = new Player("Player1", 100);
        player2 = new Player("Player2", 50);
        player3 = new Player("Player3", 0); // All-in player
    }

    @Test
    void testSmallPotAndBigPotCalculation() {
        // Player1 bets 30 chips
        pot.addChips(player1, 30);
        assertEquals(30, pot.getSmallPot());
        assertEquals(0, pot.getBigPot());

        // Player2 bets 20 chips
        pot.addChips(player2, 20);
        assertEquals(50, pot.getSmallPot());
        assertEquals(0, pot.getBigPot());

        // Player3 goes all-in with 0 chips
        pot.addChips(player3, 0);
        assertEquals(50, pot.getSmallPot());
        assertEquals(0, pot.getBigPot());

        // Player1 bets an additional 40 chips
        pot.addChips(player1, 40);
        assertEquals(90, pot.getSmallPot());
        assertEquals(0, pot.getBigPot());

        // Player2 bets an additional 30 chips
        pot.addChips(player2, 30);
        assertEquals(120, pot.getSmallPot());
        assertEquals(0, pot.getBigPot());
    }
}
