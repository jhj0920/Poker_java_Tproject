// 카드 덱을 관리하고, 셔플 및 카드 분배를 담당하는 클래스
import java.util.*;

/** Deck.java
 * This class manages a deck of cards, allowing shuffling and dealing cards.
 * It contains 52 unique cards and provides methods to shuffle and deal cards.
 */

public class Deck {
    private final List<Card> cards;       // 카드 목록 (52장)
    private int currentIndex;             // 현재 분배할 카드 인덱스

    public Deck() {
        cards = new ArrayList<>();
        // 52장의 고유 카드 생성
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
        shuffle(); // 생성 후 즉시 섞기
        currentIndex = 0;
    }

    // 덱을 무작위로 섞음
    public void shuffle() {
        Collections.shuffle(cards);
        currentIndex = 0;
    }

    // 카드 한 장을 분배함 (중복 없음)
    public Card dealCard() {
        if (currentIndex >= cards.size()) {
            throw new IllegalStateException("덱에 카드가 더 이상 없습니다.");
        }
        return cards.get(currentIndex++);
    }

    // 남은 카드 수 반환
    public int remainingCards() {
        return cards.size() - currentIndex;
    }
}