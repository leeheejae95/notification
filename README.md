
## 프로젝트 개요
  #### 알림(consumer)서비스 개발 프로젝트
---

## 주요 기능
  #### 01. 알림센터 요규사항 분석 및 설계
  #### 02. 알림센터 프로젝트 구성
  #### 03. 알림 도메인 생성 및 저장 구현
  #### 04. Kafka Event  Consumer 구현
  #### 05. 알림 종류에 따른 알림생성/삭제 구현
  #### 06. 알림 목록 조회 API구현
  #### 07. 기타 알림센터 API구현
  #### 08. 알림센터 성능 최적화

## 기술 스택

- **Java 21**
- **Spring Boot 3.x**
- **Apache Kafka**
- **Spring Data JPA**
- **MongoDB**
- **Lombok**
- **Gradle**

---
```plaintext
notification/
├── settings.gradle.kts
├── build.gradle.kts                  # 루트: 공통 플러그인/버전/리포지토리
├── gradle/
│   └── ...                           # Gradle Wrapper
├── .docker/
│   └── docker-compose.yml            # zookeeper/kafka/redis/mongo 등 로컬 인프라
├── core                              # 도메인·저장소·공통 설정 모듈
│   ├── build.gradle.kts
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── com.example.notification
│       │   │       ├── common                         # 공통
│       │   │       │   ├── config                     # Mongo/Redis/공통 Bean 설정
│       │   │       │   ├── error                      # 에러 코드/응답 규격
│       │   │       │   └── exception                  # 공통 예외/핸들러 베이스
│       │   │       └── domain                         # 도메인 계층
│       │   │           ├── model                      # 엔티티/레코드(예: Notification)
│       │   │           ├── repository                 # Port(인터페이스)
│       │   │           └── service                    # 도메인 서비스(비즈 규칙)
│       │   └── resources
│       │       └── application.yaml                   # 코어 모듈 기본 설정
│       └── test
│           └── java
│               └── ...                                # JUnit5/Testcontainers 등
│
├── api                               # REST API 수신/조회 모듈
│   ├── build.gradle.kts
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── com.example.notification.api
│       │   │       ├── presentation                  # 프레젠테이션 계층
│       │   │       │   └── controller                # REST 컨트롤러
│       │   │       ├── application                   # 애플리케이션 계층
│       │   │       │   ├── command                   # 요청 Command 객체
│       │   │       │   ├── facade                    # 유즈케이스 파사드
│       │   │       │   └── processor                 # 유즈케이스 처리기
│       │   │       ├── infrastructure                # 인프라(발행/연동)
│       │   │       │   ├── kafka                     # Kafka Producer/Config
│       │   │       │   └── mapper                    # DTO ↔ 도메인 매핑
│       │   │       └── common                        # API 공통
│       │   │           ├── dto                       # request/response DTO
│       │   │           ├── validation                # 입력 검증
│       │   │           └── exceptionhandler          # ControllerAdvice
│       │   └── resources
│       │       ├── application.yaml                  # API 기본 설정
│       │       ├── application-local.yaml            # 로컬 프로필(API)
│       │       └── static/docs                       # OpenAPI/Swagger 정적 리소스(선택)
│       └── test
│           └── java
│               └── ...                               # WebMvcTest/통합테스트
│
├── consumer                         # Kafka 이벤트 컨슈머 모듈
│   ├── build.gradle.kts
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── com.example.notification.consumer
│       │   │       ├── application                   # 애플리케이션 계층
│       │   │       │   ├── handler                   # 이벤트별 핸들러(comment/like/follow)
│       │   │       │   └── processor                 # 처리 흐름/트랜잭션 경계
│       │   │       ├── domain                        # 도메인 계층
│       │   │       │   ├── event                     # 이벤트 모델(메시지 스키마)
│       │   │       │   └── policy                    # 재시도/백오프/멱등 규칙
│       │   │       ├── infrastructure                # 인프라 계층
│       │   │       │   ├── kafka                     # Consumer, DLT, 컨테이너 설정
│       │   │       │   └── mapper                    # 이벤트→도메인 변환
│       │   │       └── common                        # 공통(로그/메트릭)
│       │   └── resources
│       │       ├── application.yaml                  # Consumer 기본 설정
│       │       ├── application-local.yaml            # 로컬 프로필(이벤트 바인딩)
│       │       └── bindings.yaml                     # Spring Cloud Stream 바인딩(선택)
│       └── test
│           └── java
│               └── ...                               # @EmbeddedKafka/Testcontainers
│
└── docs                             # 문서/다이어그램(선택)
    ├── architecture.png             # 아키텍처 다이어그램 (API→Kafka→Consumer→Mongo/Redis)
    ├── erd.png                      # ERD/주요 컬렉션 구조
    └── sequence-notification.png    # 시퀀스 다이어그램(요청→발행→소비→저장/응답)
```
