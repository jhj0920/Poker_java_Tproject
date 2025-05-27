import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("덱 생성 및 셔플 테스트");
        Deck deck = new Deck();
        System.out.println("남은 카드 수: " + deck.remainingCards());

        System.out.println("\n플레이어 핸드 구성");
        Hand playerHand = new Hand();
        for (int i = 0; i < 5; i++) {
            playerHand.addCard(deck.dealCard());
        }
        System.out.println(playerHand);

        System.out.println("핸드 정렬 후");
        playerHand.sort();
        System.out.println(playerHand);

        System.out.println("핸드 평가");
        List<Card> handCards = playerHand.getCards();
        HandEvaluator.HandRank rank = HandEvaluator.evaluateHand(handCards);
        System.out.println("현재 핸드 족보: " + rank);
    }
}
