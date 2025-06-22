package poker.logic;

// 게임의 현재 단계 상태를 정의하는 열거형
enum GameState {
    WAITING,   // 게임 대기 중
    PREFLOP,   // 프리플랍 단계 (핸드만 존재)
    FLOP,      // 플랍 (커뮤니티 카드 3장 공개)
    TURN,      // 턴 (커뮤니티 카드 4장)
    RIVER,     // 리버 (커뮤니티 카드 5장)
    SHOWDOWN   // 승자 결정
}
