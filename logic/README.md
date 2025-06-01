# logic 폴더

---

## 포함된 클래스

### 1. `Action.java`
- 플레이어가 수행할 수 있는 행동 정의 enum
- 예: `FOLD`, `CALL`, `RAISE`, `CHECK`, `ALL_IN`

### 2. `BettingRoundManager.java`
- 각 베팅 라운드를 처리하는 유틸리티 클래스
- 플레이어의 입력에 따라 콜, 레이즈, 폴드, 체크 진행
- 팟과 칩을 갱신

### 3. `Card.java`
- 포커 카드 클래스
- 슈트(Suit)와 랭크(Rank)를 Enum으로 구성

### 4. `Deck.java`
- 카드 52장을 초기화하고 셔플하는 덱 클래스
- `shuffle()` : 덱을 섞음
- `dealCard()` : 카드 한 장 꺼냄

### 5. `GameManager.java`
- 전체 게임 흐름 제어 클래스
- 딜러 순서 지정, 단계 전환, 커뮤니티 카드 공개 수행
- 각 플레이어에게 핸드 카드 지급

### 6. `GameState.java`
- 게임 진행 단계 정의 enum
- 예: `WAITING`, `PREFLOP`, `FLOP`, `TURN`, `RIVER`, `SHOWDOWN`

### 7. `Hand.java`
- 플레이어의 패(손에 든 카드) 관리
- 최대 5장까지 추가 가능
- `sort()`로 높은 순서대로 정렬
- `toString()`은 패를 출력

### 8. `HandEvaluator.java`
- 패의 족보를 판단하는 클래스
- 족보 예시: `HIGH_CARD`, `ONE_PAIR`, `TWO_PAIR`, `FLUSH`, `FULL_HOUSE`, `STRAIGHT_FLUSH`, `ROYAL_FLUSH` 등
- `evaluateHand(List<Card>)`로 족보를 판별하고
- `determineWinner(...)`로 승자를 결정

### 9. `Main.java`
- 전체 게임 실행을 담당하는 메인 클래스
- 플레이어 초기화, 라운드 진행, 족보 판정, 결과 출력까지 포함

### 10. `Player.java`
- 플레이어 정보 클래스
- 이름, 칩 개수, 핸드, 폴드 여부 등을 관리
- 베팅 상태 초기화 기능 포함

### 11. `Pot.java`
- 플레이어별 판돈 기여를 추적하는 클래스
- `addChips(Player, amount)`로 칩 추가
- `getTotal()`로 전체 팟 반환
- `reset()`으로 라운드 초기화

---

## 테스트

- `Main.java`를 실행하여 전체 로직을 테스트 가능
- 예시 흐름:
  1. 4명의 플레이어 생성 후 각각 10,000칩 지급
  2. 각 플레이어에게 핸드 카드 2장 지급
  3. `PREFLOP → FLOP → TURN → RIVER` 순서로 커뮤니티 카드 5장 공개
  4. 각 단계별로 베팅 가능 (콜, 레이즈, 체크, 폴드)
  5. 최종 족보 판단 후 승자에게 팟 지급

---

## 예시 실행

```bash
javac *.java
java Main
