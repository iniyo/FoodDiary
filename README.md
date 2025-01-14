# Food Diary Server 개발
[Spring Project 생성 사이트](https://start.spring.io/)


## 개요
Food Diary 서비스를 제공하기 위한 Server측 Application입니다.
현재 FCM을 통해 token 생성된 디바이스에 알림을 보내는 역할을 수행합니다.

---

## 프로젝트 구조
DDD(Domain-Driven Design) 기반으로 다음과 같은 4가지 계층으로 구성:

1. **Interface Layer**:
    - 클라이언트와 직접 통신하는 API 컨트롤러
    - `RestController`와 DTO 객체 정의

2. **Application Layer**:
    - 유스케이스와 비즈니스 로직
    - FCM 전송, 사용자 검색 등의 주요 동작 구현

3. **Domain Layer**:
    - 핵심 도메인 모델 정의
    - Notification, User 등

4. **Infrastructure Layer**:
    - 데이터베이스 및 외부 서비스 연동
    - JPA 리포지토리, Firebase 클라이언트, Kafka 등

---

## 주요 API

### 1. **단일 알림 전송 API**
- **Endpoint**: `POST /fcm/single`
- **권한**: 인증된 사용자
- **설명**: 특정 사용자에게 알림 전송
  ```json
  {
  "userEmail": "user@example.com",
  "contentId": 12345,
  "notifyType": "INFO",
  "title": "Welcome!",
  "content": "Thank you for signing up!"
  }

### 2. **다중 알림 전송 API**
- **Endpoint**: `POST /fcm/multi`
- **권한**: 인증된 사용자
- **설명**: 모든 사용자에게 알림 전송
  ```json
  {
  "contentId": 67890,
  "notifyType": "ALERT",
  "title": "System Maintenance",
  "content": "Scheduled maintenance at 12:00 AM."
  }
  
## 향후 개선 방향
### Outbox Pattern 실패 처리 개선
현재 Outbox Pattern에서 최대 재시도 횟수 초과 후의 처리가 미흡한 상태입니다. 다음 기능들의 추가가 필요합니다:

1. **DeadLetter Queue 도입**
- 실패한 메시지를 DLQ로 이동
- 추후 분석 및 재처리를 위한 메시지 보관

2. **관리자 알림 시스템**
- 메시지 처리 실패 시 관리자에게 알림
- 실패 원인과 메시지 정보 포함

3. **모니터링 시스템**
- 실패율 모니터링
- 실패 패턴 분석
- 메시지 처리 현황 대시보드

OutboxScheduler.java의 실패 처리 로직
```
else 실패
    FCM --> FcmClient: Error Response
    FcmClient --> Scheduler: Error
    alt 재시도 가능
        Scheduler -> OutboxRepo: retryCount 증가
    else 재시도 불가
        Scheduler -> OutboxRepo: deadLetter = true
        Scheduler -> Producer: 실패 이벤트 발행
        // TODO: 아래 기능 구현 필요
        Scheduler -> DLQ: 실패 메시지 저장
        Scheduler -> Alert: 관리자 알림 발송
```

## 참고 사이트

- Spring DDD
  [SpringId - DDD with Spring by Maciej (Youtube)](https://www.youtube.com/watch?v=VGhg6Tfxb60&t=2537s)
  [Spring boot - DDD (Github)](https://github.com/sandokandias/spring-boot-ddd)

- FCM
  [Spring Boot 정기 푸시 알림 전송 배치 프로세스 (Tistory)](https://jaeseo0519.tistory.com/397)
  [프로젝트 삽질기 feat FCM 공식문서 (Tistory)](https://overcome-the-limits.tistory.com/630#1.-%EC%84%9C%EB%B2%84-%ED%99%98%EA%B2%BD-%EB%B0%8F-fcm)
  [※ FCM 뿐 아니라 서버 아키텍처 등 좋음](https://zuminternet.github.io/FCM-PUSH/)

- 스프링 시큐리티, OAuth, 인증 프로세스
  [spring security OAuth2 동작 원리 (Velog)](https://velog.io/@nefertiri/%EC%8A%A4%ED%94%84%EB%A7%81-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0-OAuth2-%EB%8F%99%EC%9E%91-%EC%9B%90%EB%A6%AC#oauth2userrequestentityconverter)
  [스프링 시큐리티 - 인증 프로세스(Velog)](https://velog.io/@impala/Spring-Security-%EC%8A%A4%ED%94%84%EB%A7%81-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0-%EC%9D%B8%EC%A6%9D-%ED%94%84%EB%A1%9C%EC%84%B8%EC%8A%A4)
  [Spring security JWT 구현하기](https://m42-orion.tistory.com/151)

- spring swagger
  [Spring Swagger의 운영코드 침투 막기](https://jaeseo0519.tistory.com/406)
  [추가](https://haward.tistory.com/251)

## 참고

### git convention
feat: 새로운 기능 추가
fix: 버그 수정
docs: 문서 수정
style: 코드 스타일 변경 (코드 포매팅, 세미콜론 누락 등)
design: 사용자 UI 디자인 변경 (CSS 등)
test: 테스트 코드, 리팩토링 (Test Code)
refactor: 리팩토링 (Production Code)
build: 빌드 파일 수정
ci: CI 설정 파일 수정
perf: 성능 개선
chore: 자잘한 수정이나 빌드 업데이트
rename: 파일 혹은 폴더명을 수정만 한 경우
remove: 파일을 삭제만 한 경우