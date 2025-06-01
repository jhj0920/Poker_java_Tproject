// 족보 판별 및 승자 결정 로직 
import java.util.*;

public class HandEvaluator {
    public enum HandRank {
        HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_OF_A_KIND,
        STRAIGHT, FLUSH, FULL_HOUSE, FOUR_OF_A_KIND,
        STRAIGHT_FLUSH, ROYAL_FLUSH
    }

    public static class HandValue implements Comparable<HandValue> {
        public final HandRank rank;
        public final List<Integer> tiebreakers;

        public HandValue(HandRank rank, List<Integer> tiebreakers) {
            this.rank = rank;
            this.tiebreakers = tiebreakers;
        }

        @Override
        public int compareTo(HandValue other) {
            if (this.rank.ordinal() != other.rank.ordinal()) {
                return Integer.compare(this.rank.ordinal(), other.rank.ordinal());
            }
            for (int i = 0; i < Math.min(this.tiebreakers.size(), other.tiebreakers.size()); i++) {
                if (!this.tiebreakers.get(i).equals(other.tiebreakers.get(i))) {
                    return Integer.compare(this.tiebreakers.get(i), other.tiebreakers.get(i));
                }
            }
            return 0;
        }
    }

    public static void determineWinner(List<Player> players, List<Card> community, Pot pot) {
        Map<Player, HandValue> handMap = new HashMap<>();
        HandValue bestHand = null;

        for (Player player : players) {
            if (player.isFolded()) continue;

            List<Card> total = new ArrayList<>(community);
            total.addAll(player.getHand().getCards());
            HandValue hv = evaluateBestHand(total);
            handMap.put(player, hv);

            System.out.println(player.getName() + "의 족보: " + hv.rank);

            if (bestHand == null || hv.compareTo(bestHand) > 0) {
                bestHand = hv;
            }
        }

        // 동점자 판별 및 수집
        List<Player> winners = new ArrayList<>();
        for (Map.Entry<Player, HandValue> entry : handMap.entrySet()) {
            if (entry.getValue().compareTo(bestHand) == 0) {
                winners.add(entry.getKey());
            }
        }

        if (!winners.isEmpty()) {
            int splitAmount = pot.getTotal() / winners.size();
            System.out.print("\n승자: ");
            for (int i = 0; i < winners.size(); i++) {
                Player winner = winners.get(i);
                winner.setChips(winner.getChips() + splitAmount);
                System.out.print(winner.getName());
                if (i < winners.size() - 1) System.out.print(", ");
            }
            System.out.println(" (" + bestHand.rank + ")");
            System.out.println("각 승자는 팟의 $" + splitAmount + "을 가져갑니다.");
        } else {
            System.out.println("\n모든 플레이어가 폴드하였습니다. 승자 없음.");
        }
    }

    public static HandValue evaluateBestHand(List<Card> cards) {
        List<List<Card>> combinations = generateFiveCardCombinations(cards);
        HandValue best = null;

        for (List<Card> combo : combinations) {
            HandValue hv = evaluateHand(combo);
            if (best == null || hv.compareTo(best) > 0) {
                best = hv;
            }
        }
        return best;
    }

    public static HandValue evaluateHand(List<Card> cards) {
        List<Card> sorted = new ArrayList<>(cards);
        sorted.sort(Comparator.comparingInt(c -> c.getRank().ordinal()));
        Collections.reverse(sorted);

        Map<Card.Rank, Integer> rankMap = getRankCountMap(sorted);
        boolean isFlush = isFlush(sorted);
        boolean isStraight = isStraight(sorted);

        List<Integer> values = new ArrayList<>();
        for (Card c : sorted) values.add(c.getRank().ordinal());

        if (isFlush && isStraight && sorted.get(0).getRank() == Card.Rank.ACE)
            return new HandValue(HandRank.ROYAL_FLUSH, values);
        else if (isFlush && isStraight)
            return new HandValue(HandRank.STRAIGHT_FLUSH, values);
        else if (hasNOfAKind(rankMap, 4))
            return new HandValue(HandRank.FOUR_OF_A_KIND, values);
        else if (hasFullHouse(rankMap))
            return new HandValue(HandRank.FULL_HOUSE, values);
        else if (isFlush)
            return new HandValue(HandRank.FLUSH, values);
        else if (isStraight)
            return new HandValue(HandRank.STRAIGHT, values);
        else if (hasNOfAKind(rankMap, 3))
            return new HandValue(HandRank.THREE_OF_A_KIND, values);
        else if (hasTwoPair(rankMap))
            return new HandValue(HandRank.TWO_PAIR, values);
        else if (hasNOfAKind(rankMap, 2))
            return new HandValue(HandRank.ONE_PAIR, values);
        else
            return new HandValue(HandRank.HIGH_CARD, values);
    }

    private static boolean isFlush(List<Card> cards) {
        Card.Suit suit = cards.get(0).getSuit();
        for (Card c : cards) if (c.getSuit() != suit) return false;
        return true;
    }

    private static boolean isStraight(List<Card> cards) {
        Set<Integer> ranks = new TreeSet<>();
        for (Card c : cards) ranks.add(c.getRank().ordinal());

        if (ranks.contains(12) && ranks.contains(0) && ranks.contains(1) &&
            ranks.contains(2) && ranks.contains(3)) return true; // A-2-3-4-5

        List<Integer> list = new ArrayList<>(ranks);
        for (int i = 0; i <= list.size() - 5; i++) {
            boolean straight = true;
            for (int j = 0; j < 4; j++) {
                if (list.get(i + j) + 1 != list.get(i + j + 1)) {
                    straight = false;
                    break;
                }
            }
            if (straight) return true;
        }
        return false;
    }

    private static Map<Card.Rank, Integer> getRankCountMap(List<Card> cards) {
        Map<Card.Rank, Integer> map = new HashMap<>();
        for (Card c : cards) map.put(c.getRank(), map.getOrDefault(c.getRank(), 0) + 1);
        return map;
    }

    private static boolean hasNOfAKind(Map<Card.Rank, Integer> rankMap, int n) {
        return rankMap.containsValue(n);
    }

    private static boolean hasFullHouse(Map<Card.Rank, Integer> rankMap) {
        boolean hasThree = false, hasTwo = false;
        for (int count : rankMap.values()) {
            if (count == 3) hasThree = true;
            else if (count == 2) hasTwo = true;
        }
        return hasThree && hasTwo;
    }

    private static boolean hasTwoPair(Map<Card.Rank, Integer> rankMap) {
        int pairCount = 0;
        for (int count : rankMap.values()) {
            if (count == 2) pairCount++;
        }
        return pairCount >= 2;
    }

    private static List<List<Card>> generateFiveCardCombinations(List<Card> cards) {
        List<List<Card>> combinations = new ArrayList<>();
        int n = cards.size();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                List<Card> combo = new ArrayList<>();
                for (int k = 0; k < n; k++) {
                    if (k != i && k != j) combo.add(cards.get(k));
                }
                combinations.add(combo);
            }
        }
        return combinations;
    }
}
