package poker.logic;

// 족보 판별 및 승자 결정 로직 
import java.util.*;

/* HandEvaluator.java
 * This class evaluates poker hands and determines the winner among players based on their hands and community cards.
 * It provides methods to evaluate the best hand, determine the winner, and compare hand values.
 * It includes logic for various poker hand rankings such as High Card, One Pair, Two Pair, etc.
 */
public class HandEvaluator {
    public enum HandRank {
        HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_OF_A_KIND,
        STRAIGHT, FLUSH, FULL_HOUSE, FOUR_OF_A_KIND,
        STRAIGHT_FLUSH, ROYAL_FLUSH
    }

    /**
	 * HandValue class represents the value of a poker hand.
	 * It uses HandRank to indicate the type of hand and a list of tiebreakers
	 * @returns the rank and tiebreakers for comparison
	 */
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

    /**
     * Determines the winner of the current hand and updates player chips.
     *
     * @param players    list of players taking part in the hand
     * @param community  community cards on the table
     * @param pot        the pot containing the chips bet this round
     * @return a human readable summary of the result
     */
    public static String determineWinner(List<Player> players, List<logicCard> community, Pot pot) {
        Map<Player, HandValue> handMap = new HashMap<>();

        // 각 플레이어의 베스트 핸드를 계산
        for (Player player : players) {
            if (player.isFolded()) continue;

            List<logicCard> total = new ArrayList<>(community);
            total.addAll(player.getHand().getCards());
            HandValue hv = evaluateBestHand(total);
            handMap.put(player, hv);

            System.out.println(player.getName() + "의 족보: " + hv.rank);
        }
        
        if (handMap.isEmpty()) {
            String msg = "모든 플레이어가 폴드하였습니다. 승자 없음.";
            System.out.println("\n" + msg);
            return msg;
        }

        // 메인 팟 승자 계산
        HandValue bestMain = null;
        for (HandValue hv : handMap.values()) {
            if (bestMain == null || hv.compareTo(bestMain) > 0) bestMain = hv;
        }

        List<Player> mainWinners = new ArrayList<>();
        for (Map.Entry<Player, HandValue> e : handMap.entrySet()) {
            if (e.getValue().compareTo(bestMain) == 0) mainWinners.add(e.getKey());
        }

        Map<Player, Integer> payouts = new HashMap<>();
        int mainPot = pot.getSmallPot();
        if (mainPot > 0) {
            int split = mainPot / mainWinners.size();
            int rem = mainPot % mainWinners.size();
            for (int i = 0; i < mainWinners.size(); i++) {
                Player w = mainWinners.get(i);
                payouts.put(w, payouts.getOrDefault(w, 0) + split + (i < rem ? 1 : 0));
            }
        }

        // 사이드 팟이 있을 경우 계산
        if (pot.getBigPot() > 0) {
            int threshold = pot.getMinAllIn();
            List<Player> sideParticipants = new ArrayList<>();
            for (Player p : players) {
                if (!p.isFolded() && pot.getContribution(p) > threshold) {
                    sideParticipants.add(p);
                }
            }
            HandValue bestSide = null;
            for (Player p : sideParticipants) {
                HandValue hv = handMap.get(p);
                if (bestSide == null || hv.compareTo(bestSide) > 0) bestSide = hv;
            }

            List<Player> sideWinners = new ArrayList<>();
            for (Player p : sideParticipants) {
                if (handMap.get(p).compareTo(bestSide) == 0) sideWinners.add(p);
            }

            int sidePot = pot.getBigPot();
            int split = sidePot / sideWinners.size();
            int rem = sidePot % sideWinners.size();
            for (int i = 0; i < sideWinners.size(); i++) {
                Player w = sideWinners.get(i);
                payouts.put(w, payouts.getOrDefault(w, 0) + split + (i < rem ? 1 : 0));
            }
        }

        // 칩 지급 및 메시지 생성
        StringBuilder sb = new StringBuilder();
        sb.append("승자: ");
        boolean first = true;
        for (Map.Entry<Player, Integer> e : payouts.entrySet()) {
            Player p = e.getKey();
            p.setChips(p.getChips() + e.getValue());
            if (!first) sb.append(", ");
            sb.append(p.getName());
            first = false;
        }
        sb.append("\n");
        sb.append("메인 팟 $" + pot.getSmallPot());
        if (pot.getBigPot() > 0) {
            sb.append(" | 사이드 팟 $" + pot.getBigPot());
        }

        System.out.println("\n" + sb);
        return sb.toString();
    }

    /**
     * evaluateBestHand evaluates the best possible hand from a list of cards.
     * @param cards the list of cards to evaluate
     * @return HandValue representing the best hand and its rank
     */
    public static HandValue evaluateBestHand(List<logicCard> cards) {
        List<List<logicCard>> combinations = generateFiveCardCombinations(cards);
        HandValue best = null;

        for (List<logicCard> combo : combinations) {
            HandValue hv = evaluateHand(combo);
            if (best == null || hv.compareTo(best) > 0) {
                best = hv;
            }
        }
        return best;
    }

    /**
	 * evaluateHand evaluates a given hand of cards and returns its HandValue.
	 * It checks for various poker hands and ranks them accordingly.
	 * @param cards the list of cards to evaluate
	 * @return HandValue representing the best hand and its rank
	 */
    public static HandValue evaluateHand(List<logicCard> cards) {
        List<logicCard> sorted = new ArrayList<>(cards);
        sorted.sort(Comparator.comparingInt(c -> c.getRank().ordinal()));
        Collections.reverse(sorted);

        Map<logicCard.Rank, Integer> rankMap = getRankCountMap(sorted);
        boolean isFlush = isFlush(sorted);
        int straightHigh = getStraightHigh(sorted);
        boolean isStraight = straightHigh >= 0;

        List<Integer> values = new ArrayList<>();
        for (logicCard c : sorted) values.add(c.getRank().ordinal());

        boolean isRoyal = isFlush && isStraight &&
                straightHigh == logicCard.Rank.ACE.ordinal() &&
                values.contains(logicCard.Rank.TEN.ordinal());

        if (isRoyal) {
            values.clear();
            values.add(straightHigh);
            return new HandValue(HandRank.ROYAL_FLUSH, values);
        } else if (isFlush && isStraight) {
            values.clear();
            values.add(straightHigh);
            return new HandValue(HandRank.STRAIGHT_FLUSH, values);
        }
        else if (hasNOfAKind(rankMap, 4))
            return new HandValue(HandRank.FOUR_OF_A_KIND, values);
        else if (hasFullHouse(rankMap))
            return new HandValue(HandRank.FULL_HOUSE, values);
        else if (isFlush)
            return new HandValue(HandRank.FLUSH, values);
        else if (isStraight) {
            values.clear();
            values.add(straightHigh);
            return new HandValue(HandRank.STRAIGHT, values);
        }
        else if (hasNOfAKind(rankMap, 3))
            return new HandValue(HandRank.THREE_OF_A_KIND, values);
        else if (hasTwoPair(rankMap))
            return new HandValue(HandRank.TWO_PAIR, values);
        else if (hasNOfAKind(rankMap, 2))
            return new HandValue(HandRank.ONE_PAIR, values);
        else
            return new HandValue(HandRank.HIGH_CARD, values);
    }

    /**
	 * isFlush checks if all cards in the hand have the same suit.
	 * @param cards the list of cards to check
	 * @return true if all cards have the same suit, false otherwise
	 */
    private static boolean isFlush(List<logicCard> cards) {
        logicCard.Suit suit = cards.get(0).getSuit();
        for (logicCard c : cards) if (c.getSuit() != suit) return false;
        return true;
    }

    /**
     * isStraight checks if the hand contains a sequence of five consecutive ranks.
     * @param cards the list of cards to check
     * @return true if the hand contains a straight, false otherwise
     */
    private static boolean isStraight(List<logicCard> cards) {
        return getStraightHigh(cards) >= 0;
    }

    /**
     * Returns the high card ordinal if the list forms a straight or -1 if not.
     * Handles the special wheel case (A-2-3-4-5).
     */
    private static int getStraightHigh(List<logicCard> cards) {
        Set<Integer> ranks = new TreeSet<>();
        for (logicCard c : cards) ranks.add(c.getRank().ordinal());

        if (ranks.contains(12) && ranks.contains(0) && ranks.contains(1)
                && ranks.contains(2) && ranks.contains(3)) {
            return 3; // Five-high straight
        }

        List<Integer> list = new ArrayList<>(ranks);
        for (int i = 0; i <= list.size() - 5; i++) {
            boolean straight = true;
            for (int j = 0; j < 4; j++) {
                if (list.get(i + j) + 1 != list.get(i + j + 1)) {
                    straight = false;
                    break;
                }
            }
            if (straight) {
                return list.get(i + 4);
            }
        }
        return -1;
    }

    /**
     * getRankCountMap creates a map of card ranks to their counts in the hand.
     * This is used to determine the presence of pairs, three-of-a-kinds, etc.
     * @param cards the list of cards to analyze
     * @return a map where keys are card ranks and values are their counts
     */
    private static Map<logicCard.Rank, Integer> getRankCountMap(List<logicCard> cards) {
        Map<logicCard.Rank, Integer> map = new HashMap<>();
        for (logicCard c : cards) map.put(c.getRank(), map.getOrDefault(c.getRank(), 0) + 1);
        return map;
    }

    /**
	 * hasNOfAKind checks if the hand contains at least one rank with exactly n occurrences.
	 * @param rankMap the map of card ranks to their counts
	 * @param n the number of occurrences to check for
	 * @return true if there is a rank with exactly n occurrences, false otherwise
	 */
    private static boolean hasNOfAKind(Map<logicCard.Rank, Integer> rankMap, int n) {
        return rankMap.containsValue(n);
    }

    /**
     * hasFullHouse checks if the hand contains a full house (three of one rank and two of another).
     * @param rankMap the map of card ranks to their counts
     * @return true if the hand contains a full house, false otherwise
     */
    private static boolean hasFullHouse(Map<logicCard.Rank, Integer> rankMap) {
        boolean hasThree = false, hasTwo = false;
        for (int count : rankMap.values()) {
            if (count == 3) hasThree = true;
            else if (count == 2) hasTwo = true;
        }
        return hasThree && hasTwo;
    }

    /**
	 * hasTwoPair checks if the hand contains at least two pairs.
	 * @param rankMap the map of card ranks to their counts
	 * @return true if the hand contains at least two pairs, false otherwise
	 */
    private static boolean hasTwoPair(Map<logicCard.Rank, Integer> rankMap) {
        int pairCount = 0;
        for (int count : rankMap.values()) {
            if (count == 2) pairCount++;
        }
        return pairCount >= 2;
    }

    /**
	 * generateFiveCardCombinations generates all possible combinations of five cards from the given list.
	 * This is used to evaluate the best hand from a larger set of cards.
	 * @param cards the list of cards to generate combinations from
	 * @return a list of lists, each containing a combination of five cards
	 */
    private static List<List<logicCard>> generateFiveCardCombinations(List<logicCard> cards) {
        List<List<logicCard>> combinations = new ArrayList<>();
        int n = cards.size();
        if (n < 5) return combinations;

        // Generate all combinations of 5 cards from the given list
        // Using a nested loop to select 5 cards from the list
        // This algorithm is O(n^5) in complexity, which is acceptable for small n (like 7 or 8 cards)
        for (int a = 0; a < n - 4; a++) {
            for (int b = a + 1; b < n - 3; b++) {
                for (int c = b + 1; c < n - 2; c++) {
                    for (int d = c + 1; d < n - 1; d++) {
                        for (int e = d + 1; e < n; e++) {
                            List<logicCard> combo = new ArrayList<>(5);
                            combo.add(cards.get(a));
                            combo.add(cards.get(b));
                            combo.add(cards.get(c));
                            combo.add(cards.get(d));
                            combo.add(cards.get(e));
                            combinations.add(combo);
                        }
                    }
                }
            }
        }
        return combinations;
    }
}
