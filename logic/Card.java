package logic;

public class Card {
    public enum Suit {
        HEART("HEART"),
        DIAMOND("DIAMOND"),
        CLUB("CLUB"),
        SPADE("SPADE");

        private final String name;
        Suit(String name) { this.name = name; }
        public String getName() { return name; }
    }

    public enum Rank {
        TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"),
        EIGHT("8"), NINE("9"), TEN("10"),
        JACK("J"), QUEEN("Q"), KING("K"), ACE("A");

        private final String symbol;
        Rank(String symbol) { this.symbol = symbol; }
        public String getSymbol() { return symbol; }
    }

    private final Suit suit;
    private final Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() { return suit; }
    public Rank getRank() { return rank; }

    @Override
    public String toString() {
        return "[" + suit.getName() + " " + rank.getSymbol() + "]";
    }
}
