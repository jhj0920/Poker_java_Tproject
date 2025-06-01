// 게임의 전체 흐름을 관리하는 클래스
import java.util.*;

public class GameManager {
    private final List<Player> players;           // 플레이어 목록
    private final Deck deck;                      // 카드 덱
    private final Pot pot;                        // 판돈 관리
    private final List<Card> communityCards;      // 커뮤니티 카드 목록
    private GameState state;                      // 현재 게임 단계
    private int dealerIndex;                      // 딜러 인덱스 (턴 순서용)

    public GameManager(List<Player> players) {
        this.players = players;
        this.deck = new Deck();
        this.pot = new Pot();
        this.communityCards = new ArrayList<>();
        this.state = GameState.WAITING;
        this.dealerIndex = 0;
    }

    // 새로운 라운드 시작 (카드 분배 및 초기화)
    public void startNewRound() {
        deck.shuffle(); // 덱 셔플
        communityCards.clear();
        pot.reset();

        // 각 플레이어에게 핸드 카드 2장 분배
        for (Player p : players) {
            p.resetForNewRound();
            p.getHand().addCard(deck.dealCard());
            p.getHand().addCard(deck.dealCard());
        }

        state = GameState.PREFLOP;
    }

    // 다음 게임 단계로 이동
    public void nextPhase() {
        switch (state) {
            case PREFLOP -> {
                dealCommunityCards(3); // 플랍: 3장 공개
                state = GameState.FLOP;
            }
            case FLOP -> {
                dealCommunityCards(1); // 턴: 1장 추가
                state = GameState.TURN;
            }
            case TURN -> {
                dealCommunityCards(1); // 리버: 1장 추가
                state = GameState.RIVER;
            }
            case RIVER -> state = GameState.SHOWDOWN; // 쇼다운 진입
        }
    }

    // 커뮤니티 카드 공개 (count만큼)
    private void dealCommunityCards(int count) {
        for (int i = 0; i < count; i++) {
            communityCards.add(deck.dealCard());
        }
    }

    // Getter들
    public GameState getState() { return state; }
    public List<Card> getCommunityCards() { return communityCards; }
    public Pot getPot() { return pot; }
    public List<Player> getPlayers() { return players; }
    public void advanceDealer() { dealerIndex = (dealerIndex + 1) % players.size(); }
    public Player getDealer() { return players.get(dealerIndex); }
}
