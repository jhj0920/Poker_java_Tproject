package poker.logic;

// 플레이어가 선택할 수 있는 행동 정의

/**
 * Action enum represents the possible actions a player can take in a poker game.
 * Each action corresponds to a specific move a player can make during their turn.
 */
enum Action {
    FOLD,   // 폴드
    CHECK,  // 체크
    CALL,   // 콜
    RAISE,  // 레이즈
    ALL_IN  // 올인
}
