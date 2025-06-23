package poker.logic;

// 각 플레이어가 판돈에 얼마를 냈는지 추적하고 총 판돈을 계산하는 클래스
import java.util.HashMap;
import java.util.Map;

/**
 * Pot.java
 * This class manages the pot in a poker game, tracking the total amount of chips bet by players
 * and their individual contributions. It provides methods to add chips, retrieve the total amount,
 * and reset the pot at the end of each round.
 */
public class Pot {
    private int smallPot;  // 모든 플레이어가 참가 가능한 메인 팟
    private int bigPot;    // 일부 플레이어만 참가 가능한 사이드 팟
    private int minAllIn;  // 올인한 플레이어 중 최소 베팅 금액
    private boolean hasAllIn; // 올인 존재 여부
    private final Map<Player, Integer> contributions; // 플레이어별 기여금

    public Pot() {
        this.smallPot = 0;
        this.bigPot = 0;
        this.minAllIn = -1;
        this.hasAllIn = false;
        this.contributions = new HashMap<>();
    }

    /**
     * 플레이어가 칩을 베팅하면 각 플레이어의 기여금을 갱신하고
     * 현재 올인 여부에 따라 소팟/사이드팟을 재계산한다.
     */
    public void addChips(Player player, int amount) {
        contributions.put(player, contributions.getOrDefault(player, 0) + amount);
        recalcPots();
    }

    /** 현재 기여 상태를 기반으로 소팟/사이드팟을 계산한다. */
    private void recalcPots() {
        int total = 0;
        for (int c : contributions.values()) total += c;

        // 올인한 플레이어 중 최소 베팅 금액을 구한다
        int localMinAllIn = Integer.MAX_VALUE;
        boolean localHasAllIn = false;
        for (Map.Entry<Player, Integer> e : contributions.entrySet()) {
            Player p = e.getKey();
            if (p.getChips() == 0 && !p.isFolded()) {
                localHasAllIn = true;
                if (e.getValue() < localMinAllIn) localMinAllIn = e.getValue();
            }
        }

        this.hasAllIn = localHasAllIn;
        this.minAllIn = localHasAllIn ? localMinAllIn : -1;

        if (!localHasAllIn || localMinAllIn == Integer.MAX_VALUE) {
            // 모든 플레이어가 베팅 가능한 경우 단일 팟으로 처리
            smallPot = total;
            bigPot = 0;
            return;
        }

        // 올인이 존재하면 모든 플레이어의 기여 중 최소 올인 금액까지를 소팟으로 계산
        int calcSmall = 0;
        for (int bet : contributions.values()) {
            calcSmall += Math.min(bet, localMinAllIn);
        }
        smallPot = Math.min(calcSmall, total);
        bigPot = total - smallPot;
    }

    public int getSmallPot() { return smallPot; }
    public int getBigPot() { return bigPot; }
    public int getTotal() { return smallPot + bigPot; }
    public int getContribution(Player p) { return contributions.getOrDefault(p, 0); }
    public int getMinAllIn() { return hasAllIn ? minAllIn : -1; }
    public boolean hasAllIn() { return hasAllIn; }
    
    // 라운드 종료 시 초기화
    public void reset() {
        smallPot = 0;
        bigPot = 0;
        minAllIn = -1;
        hasAllIn = false;
        contributions.clear();
    }
}
