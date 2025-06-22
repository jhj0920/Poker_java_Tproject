// 각 플레이어가 판돈에 얼마를 냈는지 추적하고 총 판돈을 계산하는 클래스
import java.util.HashMap;
import java.util.Map;

<<<<<<< HEAD
/**
 * Pot.java
 * This class manages the pot in a poker game, tracking the total amount of chips bet by players
 * and their individual contributions. It provides methods to add chips, retrieve the total amount,
 * and reset the pot at the end of each round.
 */
=======
>>>>>>> 98da8616b1685e62744995b27e33a04f05b0723e
public class Pot {
    private int total;  // 총 판돈
    private final Map<Player, Integer> contributions; // 플레이어별 기여금

    public Pot() {
        this.total = 0;
        this.contributions = new HashMap<>();
    }

    // 플레이어가 칩을 베팅하면 총액과 기여금 갱신
    public void addChips(Player player, int amount) {
        total += amount;
        contributions.put(player, contributions.getOrDefault(player, 0) + amount);
    }

    public int getTotal() { return total; }

    // 라운드 종료 시 초기화
    public void reset() {
        total = 0;
        contributions.clear();
    }
}
