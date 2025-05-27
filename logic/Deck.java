// 카드 52장을 생성하고 셔플 및 분배 기능을 제공하는 클래스

import java.util.*;

public class Deck {
    private final List<Card> cards = new ArrayList<>(); // 카드 리스트

    public Deck() {
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
        shuffle();
    }

    // 덱 셔플
    public void shuffle() {
        Collections.shuffle(cards);
    }

    // 카드 한 장 분배
    public Card dealCard() {
        if (cards.isEmpty()) throw new IllegalStateException("덱이 비었습니다.");
        return cards.remove(0);
    }

    public int remainingCards() {
        return cards.size();
    }
}
