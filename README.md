<img src="https://user-images.githubusercontent.com/55685140/176200000-c4db3707-207b-4889-b77e-b9099f2e6854.jpg" width="85%"/>
   
# ☕ SSAFYBOTTLE

### O2O(Online to Offline) 기반 Smart Store 카페앱

<br>

# 💁‍♂️ 프로젝트 소개

**SSAFYBOTTLE**은 사용중인 스마트 오더 앱의 불편함을 인식하여 편의성과 효율성을 높여

소비자와 판매자 모두에게 적합한 O2O 스마트 스토어 서비스 입니다!

<br>

# 📱 주요 서비스 화면

### 사용자 앱

|로그인|상품목록|상품상세|장바구니|
|--|--|--|--|
|![KakaoTalk_20220526_163000904](https://user-images.githubusercontent.com/55685140/176223024-b3fe9247-9177-48be-8d12-79c52eedf18a.jpg)|![KakaoTalk_20220629_004859513](https://user-images.githubusercontent.com/55685140/176223942-d11e94c0-bab7-41b0-93ac-6d2cc4ba9b15.jpg)|![KakaoTalk_20220526_173006620](https://user-images.githubusercontent.com/55685140/176220738-ea8d17b6-de55-4bc6-9c09-3c906d600d3f.jpg)|![KakaoTalk_20220526_173006620_01](https://user-images.githubusercontent.com/55685140/176220933-f5771c8d-0585-45db-a835-eb4f1c7ade57.jpg)|
   
   <br>
   
|e카드|카카오페이 충전|알림|Beacon|
|--|--|--|--|
|![KakaoTalk_20220526_205946871_05](https://user-images.githubusercontent.com/55685140/176224797-9603e512-e1e8-4459-9ba2-531caf519bff.jpg)|![KakaoTalk_20220526_205946871_01](https://user-images.githubusercontent.com/55685140/176224834-736ac6e9-0b5d-40ff-aae5-a44e1756a434.jpg)|![KakaoTalk_20220526_173006620_03](https://user-images.githubusercontent.com/55685140/176224905-50c7e4fb-78dc-4c6d-947b-55aa022c297d.jpg)|![KakaoTalk_20220520_133732732](https://user-images.githubusercontent.com/55685140/176224958-694d722c-6852-4fa9-a0ee-4a1a4a9a3f24.jpg)|
   
   <br>
   
### 관리자 앱

|로그인|포스기능|
|--|--|
|![KakaoTalk_20220526_212347764](https://user-images.githubusercontent.com/55685140/176225742-278a69fa-4d4b-4ed8-ae78-241d9e221661.jpg)|![KakaoTalk_20220526_212800573](https://user-images.githubusercontent.com/55685140/176225762-f9bc5bd6-ab16-4c01-86b8-489d403ef9aa.jpg)|
   
   <br>
   
|주문관리|매출통계|
|--|--|
|![KakaoTalk_20220526_214143137](https://user-images.githubusercontent.com/55685140/176226139-7998cbe4-2aad-4c9f-b421-b82eccb9bc72.jpg)|![KakaoTalk_20220526_215154668](https://user-images.githubusercontent.com/55685140/176226149-240de687-1a32-4db9-b2eb-08f857ed749f.jpg)|

<br>

# 🚀 주요 기능

### 사용자 앱

- **상품 리스트 조회**
    - 추천 메뉴, 상품 카테고리별 메뉴, 최근 주문 메뉴 리스트 조회
- **상품 상세 조회**
    - 상품의 상세 정보, 리뷰, 평점을 확인
- **상품 검색**
    - 원하는 상품을 검색
- **장바구니**
    - 원하는 상품들을 담아두는 장바구니
    - 장바구니에 담겨진 상품들 한번에 주문
- **바코드 e카드**
    - 바코드로 결제가 가능한 e카드로 충전 가능
- **바코드 결제**
    - 관리자 앱 포스기능에서 바코드로 결제 가능
- **카카오페이 충전**
    - 카카오페이로 e카드 금액 충전
- **NFC 쿠폰**
    - NFC 쿠폰을 태깅하면 e카드에 금액이 충전
- **매장 비콘 탐지**
    - 매장 주면이라면 비콘을 탐지하여 다이얼로그 띄우기

### 관리자 앱

- **포스기**
    - 매장에서 포스기능으로 메뉴를 선택하고 바코드로 결제 가능
- **주문 상태 변경**
    - 주문 접수 및 메뉴 준비완료 처리 상태 변경, FCM으로 알림 보내기
- **매출 관리**
    - 상품 종류별, 상품별, 전체 매출 조회
- **상품 관리**
    - 상품 정보 변경 및 상품 리뷰 관리

<br>

# 💻 사용기술 및 적용한 패턴
### Android
- MVVM + Repository pattern
- Singleton Pattern
- Observer pattern
- Single activity multiple fragments
- Jetpack
- Retrofit
- Coroutine
- Glide
- FCM
- Bootpay API
- Google Login API
- Zxing barcode API
- NFC
- Beacon

### Vue.js
- MVVM pattern
- Vuex
- Bootstrap
- Cookie

### Spring boot
- MVC pattern
- Single pattern
- MyBatis
- Swagger
- DI

<br>

# ⚙ 개발환경
- Android Studio Arctic Fox
- Vue.js
- Spring boot
- MySQL
- Git
