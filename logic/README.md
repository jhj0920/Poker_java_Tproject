# logic 폴더

텍사스 홀덤 포커 게임에서의 핵심 게임 로직을 포함하는 패키지입니다.

---

## 포함된 클래스

### 1. `Card.java`
- 포커 카드 클래스
- 슈트(Suit)와 랭크(Rank)를 Enum으로 구성
- 안전한 문자열(HEART, SPADE 등) 기반 출력

### 2. `Deck.java`
- 카드 52장을 초기화하고 섞는 덱 클래스
- `shuffle()` : 덱을 섞음
- `draw()` : 카드 한 장 꺼냄

### 3. `GameState.java`
- 플레이어 핸드 및 커뮤니티 카드 관리
- `Map<String, List<Card>> playerHands`로 플레이어 별 카드 저장
- 커뮤니티 카드 5장 생성 포함

### 4. `TestDeal.java`
- 위 클래스들을 테스트하는 main 메서드 제공
- 콘솔에 핸드 및 커뮤니티 카드 출력

---

## 향후 추가될 클래스 (예정)

- `PokerHandEvaluator.java` : 족보 판별 알고리즘
- `TurnManager.java` : 턴 흐름, 베팅, 콜, 폴드 관리

---

## 사용 예시 (테스트 실행)

```bash
javac logic/*.java
java logic.TestDeal
