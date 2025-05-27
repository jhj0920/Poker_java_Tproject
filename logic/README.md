# logic 폴더

---

## 포함된 클래스

### 1. `Card.java`
- 포커 카드 클래스
- 슈트(Suit)와 랭크(Rank)를 Enum으로 구성

### 2. `Deck.java`
- 카드 52장을 초기화하고 셔플하는 덱 클래스
- `shuffle()` : 덱을 섞음
- `dealCard()` : 카드 한 장 꺼냄

### 3. `Hand.java`
- 플레이어의 패(손에 든 카드) 관리
- 최대 5장까지 추가 가능
- `sort()`로 높은 순서대로 정렬
- `toString()`은 패를 한글로 출력

### 4. `HandEvaluator.java`
- 패의 족보를 판단하는 클래스
- 결과 예시: `HIGH_CARD`, `TWO_PAIR`, `FLUSH`, `FULL_HOUSE`, ...

---

## 테스트
- Main.java 실행을 통하여 logic 테스트 가능
- 플레이어의 카드 5장을 뽑고 결과를 출력하고 카드 정렬 후 다시 한 번 결과를 출력한뒤 패의 족보를 판단해 출력한다.

