import java.util.*;

public class HandEvaluator {
    public enum HandRank {
        HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_OF_A_KIND,
        STRAIGHT, FLUSH, FULL_HOUSE, FOUR_OF_A_KIND,
        STRAIGHT_FLUSH, ROYAL_FLUSH
    }

    public static HandRank evaluateHand(List<Card> cards) {
        List<Card> sorted = new ArrayList<>(cards);
        sorted.sort(Comparator.comparingInt(c -> c.getRank().ordinal()));

        boolean isFlush = isFlush(sorted);
        boolean isStraight = isStraight(sorted);
        Map<Card.Rank, Integer> rankCount = getRankCountMap(sorted);

        if (isFlush && isStraight && sorted.get(0).getRank() == Card.Rank.TEN)
            return HandRank.ROYAL_FLUSH;
        else if (isFlush && isStraight)
            return HandRank.STRAIGHT_FLUSH;
        else if (hasNOfAKind(rankCount, 4))
            return HandRank.FOUR_OF_A_KIND;
        else if (hasFullHouse(rankCount))
            return HandRank.FULL_HOUSE;
        else if (isFlush)
            return HandRank.FLUSH;
        else if (isStraight)
            return HandRank.STRAIGHT;
        else if (hasNOfAKind(rankCount, 3))
            return HandRank.THREE_OF_A_KIND;
        else if (hasTwoPair(rankCount))
            return HandRank.TWO_PAIR;
        else if (hasNOfAKind(rankCount, 2))
            return HandRank.ONE_PAIR;
        else
            return HandRank.HIGH_CARD;
    }

    private static boolean isFlush(List<Card> cards) {
        Card.Suit suit = cards.get(0).getSuit();
        for (Card c : cards) {
            if (c.getSuit() != suit) return false;
        }
        return true;
    }

    private static boolean isStraight(List<Card> cards) {
        List<Integer> ordinals = new ArrayList<>();
        for (Card c : cards) {
            ordinals.add(c.getRank().ordinal());
        }
        Collections.sort(ordinals);

        for (int i = 0; i < ordinals.size() - 1; i++) {
            if (ordinals.get(i) + 1 != ordinals.get(i + 1)) return false;
        }

        return true;
    }

    private static Map<Card.Rank, Integer> getRankCountMap(List<Card> cards) {
        Map<Card.Rank, Integer> map = new HashMap<>();
        for (Card c : cards) {
            map.put(c.getRank(), map.getOrDefault(c.getRank(), 0) + 1);
        }
        return map;
    }

    private static boolean hasNOfAKind(Map<Card.Rank, Integer> rankMap, int n) {
        return rankMap.values().contains(n);
    }

    private static boolean hasFullHouse(Map<Card.Rank, Integer> rankMap) {
        return rankMap.values().contains(3) && rankMap.values().contains(2);
    }

    private static boolean hasTwoPair(Map<Card.Rank, Integer> rankMap) {
        int pairCount = 0;
        for (int count : rankMap.values()) {
            if (count == 2) pairCount++;
        }
        return pairCount >= 2;
    }
}