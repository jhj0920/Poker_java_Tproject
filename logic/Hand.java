import java.util.*;

public class Hand {
    private final List<Card> cards = new ArrayList<>();

    public void addCard(Card card) {
        if (cards.size() >= 5) throw new IllegalStateException("패에 이미 카드가 5장 있습니다.");
        cards.add(card);
    }

    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }

    public void clear() {
        cards.clear();
    }

    public int size() {
        return cards.size();
    }

    public void sort() {
        cards.sort(Comparator.comparingInt(c -> c.getRank().ordinal()));
        Collections.reverse(cards);
    }

    public Map<Card.Rank, Integer> getRankCountMap() {
        Map<Card.Rank, Integer> rankCount = new HashMap<>();
        for (Card c : cards) {
            rankCount.put(c.getRank(), rankCount.getOrDefault(c.getRank(), 0) + 1);
        }
        return rankCount;
    }

    public Map<Card.Suit, Integer> getSuitCountMap() {
        Map<Card.Suit, Integer> suitCount = new HashMap<>();
        for (Card c : cards) {
            suitCount.put(c.getSuit(), suitCount.getOrDefault(c.getSuit(), 0) + 1);
        }
        return suitCount;
    }

    public List<Integer> getRankOrdinals() {
        List<Integer> ordinals = new ArrayList<>();
        for (Card c : cards) {
            ordinals.add(c.getRank().ordinal());
        }
        Collections.sort(ordinals);
        return ordinals;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("플레이어의 패:\n");
        for (Card c : cards) {
            sb.append(c.toString()).append("\n");
        }
        return sb.toString();
    }
}
