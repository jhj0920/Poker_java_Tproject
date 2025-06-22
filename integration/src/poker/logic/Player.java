package poker.logic;

// 플레이어의 이름, 칩, 패, 상태 등을 관리하는 클래스
public class Player {
    private final String name;           // 플레이어 이름
    private final Hand hand;             // 플레이어 패
    private int chips;                   // 현재 칩 개수
    private boolean folded;              // 폴드 여부
    private int currentBet;              // 현재 베팅 금액

    /**
	 * Player 생성자
	 * @param name 플레이어 이름
	 * @param startingChips 시작 칩 개수
	 */
    public Player(String name, int startingChips) {
        this.name = name;
        this.chips = startingChips;
        this.hand = new Hand();
        this.folded = false;
        this.currentBet = 0;
    }

    public String getName() { return name; }
    public Hand getHand() { return hand; }
    public int getChips() { return chips; }
    public void setChips(int chips) { this.chips = chips; }
    public boolean isFolded() { return folded; }
    public void fold() { this.folded = true; }
    public int getCurrentBet() { return currentBet; }
    public void setCurrentBet(int bet) { this.currentBet = bet; }

    // 라운드가 시작될 때 상태 초기화
    public void resetForNewRound() {
        hand.clear();
        folded = false;
        currentBet = 0;
    }
}
