// 포커 게임에서 사용하는 한 장의 카드를 표현하는 클래스

public class Card {
    public enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES
    }

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

    // 카드 문자열 출력
    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}