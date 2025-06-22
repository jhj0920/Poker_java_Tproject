// 포커 게임에서 사용하는 한 장의 카드를 표현하는 클래스

<<<<<<< HEAD
/* * Card.java
 * This class represents a single playing card used in poker.
 * Redundant code, exists in gui branch.
 */
=======
>>>>>>> 98da8616b1685e62744995b27e33a04f05b0723e
public class Card {
    // 카드의 무늬 (스페이드, 하트 등)
    public enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES
    }

    // 카드 숫자 (2~10, J, Q, K, A)
    public enum Rank {
        TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT,
        NINE, TEN, JACK, QUEEN, KING, ACE
    }

    private final Suit suit; // 카드 무늬
    private final Rank rank; // 카드 숫자

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    // 카드 문자열 출력 (예: ACE of SPADES)
    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
