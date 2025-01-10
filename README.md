# Food Diary Server 개발

[Spring Proejct 생성 사이트](https://start.spring.io/)

## 개발 환경
- 언어 : Java 17
- 프레임워크 : Spring Boot 3.2.12, JPA
- 빌드 도구 : gradle
- 데이터베이스 : mysql (8.0)
    * 개발서버 DB
        * IP(port) : mysql local server (3306)
        * ID/PW : root / ```1234```
        * SCHEMA : fooddiary
        * Model:
            * User
            * Notification

## 세부 요구사항
- DDD 솔루션으로 개발
  - interface(mobile/web) / infrastructure / domain / application layer로 구성
- 인증 프로세스 O
  - Spring Security
- 요청 및 응답은 자유롭게 구성
- 데이터베이스 테이블 설계 자유롭게 구성
  - book, borrow, copy, member
- 알림 설정: FCM 활용
- 이벤트 스토밍 중심으로 도메인 및 비즈니스 로직 구성

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
