
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

- **Java 17**
- **Spring Boot 3.1.4**
- **Spring Security 6.x**
- **Spring Data JPA**
- **MariaDB**
- **Lombok**
- **Gradle**

---
```plaintext
main
└── java
    └── state
        ├── admin                            # 관리자 도메인
        │   └── userManage                   #
        │       ├── application              #
        │       │   ├── command              #
        │       │   ├── common               #
        │       │   │   ├── api              #
        │       │   │   ├── error            #
        │       │   │   ├── exception        #
        │       │   │   └── exceptionhandler #
        │       │   ├── fasade               #
        │       │   └── processor            #
        │       ├── domain                   #
        │       │   ├── auth                 #
        │       │   ├── entity               #
        │       │   └── repository           #
        │       ├── infrastructure           #
        │       │   └── jpa                  #
        │       └── presentation             #
        │           ├── request              #
        │           └── response             #
        ├── common                           # 공통 설정 및 유틸리티
        │   ├── config                       # 설정 파일
        │   │   └── StateSecurityConfig      # Spring Security 설정
        │   ├── command                      # 응답 command
        │   └── exception                    # 에러 공통
        └── member                           # 회원(Member) 도메인
            ├── application                  # 애플리케이션 계층
            │   ├── command                  # Command 객체 정의
            │   │   └── member               # member command
            │   ├── fasade                   # 애플리케이션 파사드
            │   └── processor                # 비즈니스 로직 처리
            │       ├── department           # 부서 비즈니스 로직
            │       ├── member               # 사용자 비즈니스 로직
            │       └── position             # 직위 비즈니스 로직
            ├── domain                       # 도메인 계층
            │   ├── entity                   # 엔티티
            │   ├── exception                # 예외 처리
            │   │   └── custom               # custom 예외 처리
            │   └── repository               # 도메인 레벨의 Repository 인터페이스
            ├── infrastructure               # 인프라스트럭처 계층
            │   └── JPA                      # JPA 기반 Repository 구현
            └── presentation                 # 프레젠테이션 계층 (REST 컨트롤러)
                ├── apis                     # 컨트롤러
                ├── request                  # 요청 DTO
                │   └── member               # member 요청 DTO
                └── response                 # 응답 DTO
```
