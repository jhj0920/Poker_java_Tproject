// 플레이어가 소유한 카드를 관리하는 클래스

import java.util.*;

public class Hand {
    private final List<Card> cards = new ArrayList<>();

    // 카드 추가 (최대 5장 제한)
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

    // 숫자 높은 순서로 정렬
    public void sort() {
        cards.sort(Comparator.comparingInt(c -> c.getRank().ordinal()));
        Collections.reverse(cards);
    }

    // 랭크별 카드 수 계산
    public Map<Card.Rank, Integer> getRankCountMap() {
        Map<Card.Rank, Integer> rankCount = new HashMap<>();
        for (Card c : cards) {
            rankCount.put(c.getRank(), rankCount.getOrDefault(c.getRank(), 0) + 1);
        }
        return rankCount;
    }

    // 무늬별 카드 수 계산
    public Map<Card.Suit, Integer> getSuitCountMap() {
        Map<Card.Suit, Integer> suitCount = new HashMap<>();
        for (Card c : cards) {
            suitCount.put(c.getSuit(), suitCount.getOrDefault(c.getSuit(), 0) + 1);
        }
        return suitCount;
    }

    // 숫자값 리스트 반환 (족보 판별용)
    public List<Integer> getRankOrdinals() {
        List<Integer> ordinals = new ArrayList<>();
        for (Card c : cards) {
            ordinals.add(c.getRank().ordinal());
        }
        Collections.sort(ordinals);
        return ordinals;
    }

    //출력
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("플레이어의 패:\n");
        for (Card c : cards) {
            sb.append(c.toString()).append("\n");
        }
        return sb.toString();
    }
}
