// 주어진 핸드(5장 이상)에서 족보를 판별하는 클래스

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

    // 플러시 여부
    private static boolean isFlush(List<Card> cards) {
        Card.Suit suit = cards.get(0).getSuit();
        for (Card c : cards) {
            if (c.getSuit() != suit) return false;
        }
        return true;
    }

    // 스트레이트 여부
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

    // 랭크 개수 맵 생성
    private static Map<Card.Rank, Integer> getRankCountMap(List<Card> cards) {
        Map<Card.Rank, Integer> map = new HashMap<>();
        for (Card c : cards) {
            map.put(c.getRank(), map.getOrDefault(c.getRank(), 0) + 1);
        }
        return map;
    }

    // 특정 개수의 같은 숫자가 있는지 확인
    private static boolean hasNOfAKind(Map<Card.Rank, Integer> rankMap, int n) {
        return rankMap.values().contains(n);
    }

    // 풀하우스 여부
    private static boolean hasFullHouse(Map<Card.Rank, Integer> rankMap) {
        return rankMap.values().contains(3) && rankMap.values().contains(2);
    }

    // 투페어 여부
    private static boolean hasTwoPair(Map<Card.Rank, Integer> rankMap) {
        int pairCount = 0;
        for (int count : rankMap.values()) {
            if (count == 2) pairCount++;
        }
        return pairCount >= 2;
    }
}