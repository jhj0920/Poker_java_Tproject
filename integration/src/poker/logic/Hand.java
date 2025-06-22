package poker.logic;

// 플레이어의 패를 저장하고 관리하는 클래스
import java.util.*;

/**
 * Hand.java
 * This class represents a player's hand in a poker game, containing the cards they hold.
 * It provides methods to add cards, retrieve the current hand, clear the hand, sort the hand,
 * and print the hand as a string.
 */
public class Hand {
    private final List<Card> cards; // 플레이어가 들고 있는 카드들

    public Hand() {
        cards = new ArrayList<>();
    }

    // 패에 카드 추가
    public void addCard(Card card) {
        cards.add(card);
    }

    // 현재 패를 반환
    public List<Card> getCards() {
        return cards;
    }

    // 패를 비움
    public void clear() {
        cards.clear();
    }

    // 족보 판단을 위해 랭크 순으로 정렬
    public void sort() {
        cards.sort(Comparator.comparingInt(c -> c.getRank().ordinal()));
    }

    // 패를 문자열로 출력
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Card card : cards) {
            sb.append("- ").append(card.toString()).append("\n");
        }
        return sb.toString();
    }
}