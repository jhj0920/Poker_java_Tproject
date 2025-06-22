// 족보 판별 및 승자 결정 로직 
import java.util.*;

<<<<<<< HEAD
/* HandEvaluator.java
 * This class evaluates poker hands and determines the winner among players based on their hands and community cards.
 * It provides methods to evaluate the best hand, determine the winner, and compare hand values.
 * It includes logic for various poker hand rankings such as High Card, One Pair, Two Pair, etc.
 */
=======
>>>>>>> 98da8616b1685e62744995b27e33a04f05b0723e
public class HandEvaluator {
    public enum HandRank {
        HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_OF_A_KIND,
        STRAIGHT, FLUSH, FULL_HOUSE, FOUR_OF_A_KIND,
        STRAIGHT_FLUSH, ROYAL_FLUSH
    }

<<<<<<< HEAD
    /**
	 * HandValue class represents the value of a poker hand.
	 * It uses HandRank to indicate the type of hand and a list of tiebreakers
	 * @returns the rank and tiebreakers for comparison
	 */
=======
>>>>>>> 98da8616b1685e62744995b27e33a04f05b0723e
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

<<<<<<< HEAD
    /**
	 * determineWinner determines the winner of the poker game based on players' hands and community cards.
	 * @param players 플레이어 목록
	 * @param community 커뮤니티 카드 목록
	 * @param pot 판돈 객체
	 */
=======
>>>>>>> 98da8616b1685e62744995b27e33a04f05b0723e
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

<<<<<<< HEAD
    /**
     * evaluateBestHand evaluates the best possible hand from a list of cards.
     * @param cards the list of cards to evaluate
     * @return HandValue representing the best hand and its rank
     */
=======
>>>>>>> 98da8616b1685e62744995b27e33a04f05b0723e
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

<<<<<<< HEAD
    /**
	 * evaluateHand evaluates a given hand of cards and returns its HandValue.
	 * It checks for various poker hands and ranks them accordingly.
	 * @param cards the list of cards to evaluate
	 * @return HandValue representing the best hand and its rank
	 */
=======
>>>>>>> 98da8616b1685e62744995b27e33a04f05b0723e
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

<<<<<<< HEAD
    /**
	 * isFlush checks if all cards in the hand have the same suit.
	 * @param cards the list of cards to check
	 * @return true if all cards have the same suit, false otherwise
	 */
=======
>>>>>>> 98da8616b1685e62744995b27e33a04f05b0723e
    private static boolean isFlush(List<Card> cards) {
        Card.Suit suit = cards.get(0).getSuit();
        for (Card c : cards) if (c.getSuit() != suit) return false;
        return true;
    }

<<<<<<< HEAD
    /**
     * isStraight checks if the hand contains a sequence of five consecutive ranks.
     * @param cards the list of cards to check
     * @return true if the hand contains a straight, false otherwise
     */
=======
>>>>>>> 98da8616b1685e62744995b27e33a04f05b0723e
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

<<<<<<< HEAD
    /**
     * getRankCountMap creates a map of card ranks to their counts in the hand.
     * This is used to determine the presence of pairs, three-of-a-kinds, etc.
     * @param cards the list of cards to analyze
     * @return a map where keys are card ranks and values are their counts
     */
=======
>>>>>>> 98da8616b1685e62744995b27e33a04f05b0723e
    private static Map<Card.Rank, Integer> getRankCountMap(List<Card> cards) {
        Map<Card.Rank, Integer> map = new HashMap<>();
        for (Card c : cards) map.put(c.getRank(), map.getOrDefault(c.getRank(), 0) + 1);
        return map;
    }

<<<<<<< HEAD
    /**
	 * hasNOfAKind checks if the hand contains at least one rank with exactly n occurrences.
	 * @param rankMap the map of card ranks to their counts
	 * @param n the number of occurrences to check for
	 * @return true if there is a rank with exactly n occurrences, false otherwise
	 */
=======
>>>>>>> 98da8616b1685e62744995b27e33a04f05b0723e
    private static boolean hasNOfAKind(Map<Card.Rank, Integer> rankMap, int n) {
        return rankMap.containsValue(n);
    }

<<<<<<< HEAD
    /**
     * hasFullHouse checks if the hand contains a full house (three of one rank and two of another).
     * @param rankMap the map of card ranks to their counts
     * @return true if the hand contains a full house, false otherwise
     */
=======
>>>>>>> 98da8616b1685e62744995b27e33a04f05b0723e
    private static boolean hasFullHouse(Map<Card.Rank, Integer> rankMap) {
        boolean hasThree = false, hasTwo = false;
        for (int count : rankMap.values()) {
            if (count == 3) hasThree = true;
            else if (count == 2) hasTwo = true;
        }
        return hasThree && hasTwo;
    }

<<<<<<< HEAD
    /**
	 * hasTwoPair checks if the hand contains at least two pairs.
	 * @param rankMap the map of card ranks to their counts
	 * @return true if the hand contains at least two pairs, false otherwise
	 */
=======
>>>>>>> 98da8616b1685e62744995b27e33a04f05b0723e
    private static boolean hasTwoPair(Map<Card.Rank, Integer> rankMap) {
        int pairCount = 0;
        for (int count : rankMap.values()) {
            if (count == 2) pairCount++;
        }
        return pairCount >= 2;
    }

<<<<<<< HEAD
    /**
	 * generateFiveCardCombinations generates all possible combinations of five cards from the given list.
	 * This is used to evaluate the best hand from a larger set of cards.
	 * @param cards the list of cards to generate combinations from
	 * @return a list of lists, each containing a combination of five cards
	 */
=======
>>>>>>> 98da8616b1685e62744995b27e33a04f05b0723e
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
