# Notification Service (알림센터)

Kafka 이벤트를 소비해서 MongoDB에 알림을 저장하고, REST API로 조회/읽음 처리까지 제공하는 알림센터 서비스입니다.  
API 모듈과 Consumer 모듈을 분리하고, 공통 도메인은 Core 모듈로 분리하여 **확장성과 유지보수성**을 고려해 설계했습니다.

---

## 1. 주요 기능

- **알림 도메인 설계 및 저장**
    - 사용자별 알림 엔티티 설계
    - MongoDB 기반 알림 저장/조회

- **Kafka 기반 알림 Consumer**
    - 댓글, 좋아요, 팔로우 등 여러 이벤트 타입 처리
    - 이벤트 별 핸들러 분리로 유즈케이스 확장 용이

- **알림 생성 / 삭제 / 읽음 처리**
    - 알림 목록 조회 API
    - 알림 읽음 처리, 단건/다건 삭제

- **알림센터 성능 최적화 (설계 관점)**
    - 읽기 전용 기능을 MongoDB로 분리
    - 이벤트 기반 비동기 처리로 서비스 간 결합도 감소

---

## 2. 기술 스택

- **Language**: Java 21
- **Framework**: Spring Boot 3.x
- **Messaging**: Apache Kafka
- **DB**: MongoDB
- **ORM**: Spring Data JPA
- **Infra**: Docker / Docker Compose
- **Build**: Gradle (Kotlin DSL)
- **etc.**: Lombok

---

## 3. 아키텍처 개요

알림센터는 다음과 같은 흐름으로 동작합니다.

1. 외부 서비스에서 **Kafka Topic** 으로 이벤트 발행 (예: 댓글 생성, 좋아요, 팔로우 등)
2. `consumer` 모듈이 Kafka 이벤트를 **구독(consume)** 하고, 이벤트 타입에 따라 핸들러에서 비즈니스 로직 처리
3. 처리된 결과를 `core` 모듈의 도메인/레포지토리를 통해 MongoDB에 저장
4. 클라이언트(웹/앱)는 `api` 모듈의 REST API를 통해 **알림 목록 조회 / 읽음 처리 / 삭제** 수행

---

## 4. 모듈 구조

```text
notification/
├── settings.gradle.kts
├── build.gradle.kts            # 루트 공통 설정
├── gradle/
│   └── ...                     # Gradle Wrapper
├── .docker/
│   ├── docker-compose.yml      # zookeeper / kafka / mongo / redis 로컬 인프라
│   └── kafka/
│       └── docker-compose-single-kafka.yml   # 단일 Kafka 실행용
├── core/                       # 도메인, 저장소, 공통 설정 모듈
│   ├── build.gradle.kts
│   └── src/main/java/com/fc
│       ├── client/             # 외부 API 호출 Client (사용자 서비스 조회 등)
│       ├── config/             # Mongo / Redis SpringConfig (dockerProfile 포함)
│       ├── domain/             # 도메인 모델 + 서비스 + 리포지토리
│       │   ├── event/          # 이벤트용 DTO
│       │   ├── model/          # Notification / 각 알림 타입 (댓글, 팔로우 등)
│       │   ├── repository/     # MongoRepository 인터페이스 정의
│       │   └── service/        # 알림 저장/조회/삭제/신규 여부 판단 서비스
│       ├── error/              # 에러 코드 정의
│       ├── exception/          # 커스텀 예외
│       ├── response/           # 공통 응답 모델 (Response<T>)
│       └── util/               # 유틸리티 (JsonUtils 등)
├── api/                        # REST API 모듈
│   ├── build.gradle.kts
│   └── src/main/java/com/fc
│       ├── controller/         # Notification API (조회, 읽음 처리 등)
│       ├── dto/                # Request / Response DTO
│       ├── service/            # API 계층 서비스 (Facade 역할)
│       ├── mapper/             # DTO ↔ 도메인 변환
│       └── validator/          # 요청 검증기
├── consumer/                   # Kafka Consumer 모듈
│   ├── build.gradle.kts
│   └── src/main/java/com/fc
│       ├── consumer/           # Kafka Listener + 이벤트 수신 Entry Point
│       ├── controller/         # 테스트용 이벤트 수신 HTTP 엔드포인트
│       ├── event/              # Kafka에서 수신하는 이벤트 스키마 (CommentEvent 등)
│       ├── handler/            # 이벤트 유형별 핸들러 (댓글/좋아요/팔로우 등)
│       ├── processor/          # 이벤트 처리, 멱등 처리, 도메인 호출
└──     └── util/               # 공통 처리 유틸

```

## 5. 주요 코드 링크

### 5-1. 알림 조회 / 읽음 처리 API (API 모듈)

- 사용자 알림 목록 조회  
  - [UserNotificationController](api/src/main/java/com/fc/controller/UserNotificationController.java)
- 새로운 알림 여부 체크  
  - [CheckNewNotificationController](api/src/main/java/com/fc/controller/CheckNewNotificationController.java)
- 알림 읽음 처리  
  - [NotificationReadController](api/src/main/java/com/fc/controller/NotificationReadController.java)

---

### 5-2. 알림 도메인 서비스 (Core 모듈)

- 알림 저장/생성 비즈니스 로직  
  - [NotificationSaveService](core/src/main/java/com/fc/service/NotificationSaveService.java)
- 사용자 알림 목록 조회 로직  
  - [NotificationListService](core/src/main/java/com/fc/service/NotificationListService.java)
- 알림 삭제 로직  
  - [NotificationRemoveService](core/src/main/java/com/fc/service/NotificationRemoveService.java)
- 알림 단건 조회 로직  
  - [NotificationGetService](core/src/main/java/com/fc/service/NotificationGetService.java)

---

### 5-3. Kafka 이벤트 Consumer (Consumer 모듈)

- 댓글 이벤트 알림 생성  
  - [CommentEventConsumer](consumer/src/main/java/com/fc/consumer/CommentEventConsumer.java)
- 좋아요 이벤트 알림 생성  
  - [LikeEventConsumer](consumer/src/main/java/com/fc/consumer/LikeEventConsumer.java)
- 팔로우 이벤트 알림 생성  
  - [FollowEventConsumer](consumer/src/main/java/com/fc/consumer/FollowEventConsumer.java)