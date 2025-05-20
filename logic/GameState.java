package logic;

import java.util.*;

public class GameState {
    private final Map<String, List<Card>> playerHands = new LinkedHashMap<>();
    private final List<Card> communityCards = new ArrayList<>();

    public GameState(List<String> playerNames) {
        Deck deck = new Deck();
        deck.shuffle();

        for (String name : playerNames) {
            List<Card> hand = Arrays.asList(deck.draw(), deck.draw());
            playerHands.put(name, hand);
        }

        for (int i = 0; i < 5; i++) {
            communityCards.add(deck.draw());
        }
    }

    public void printState() {
        for (String name : playerHands.keySet()) {
            System.out.println(name + "의 핸드: " + playerHands.get(name));
        }
        System.out.println("커뮤니티 카드: " + communityCards);
    }

    public Map<String, List<Card>> getPlayerHands() {
        return playerHands;
    }

    public List<Card> getCommunityCards() {
        return communityCards;
    }
}
