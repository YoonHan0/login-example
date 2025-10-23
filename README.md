# 👨‍💻 Login Project

> React + Spring Boot + JWT + OAuth2 기반의 인증 예제 프로젝트  
> 회원가입 / 로그인 / 소셜 로그인 / JWT 인증 / 내정보 조회 기능을 포함합니다.

<br />

## 📚 기술 스택

| 구분 | 기술 | 설명 |
|------|------|------|
| **Frontend** | React 18 (CRA) | 회원가입, 로그인, 내정보 페이지 UI |
|  | Axios | REST API 통신 |
| **Backend** | Spring Boot 3.2 / Java 17 | REST API 서버 |
|  | Spring Security | 인증/인가, OAuth2 클라이언트 |
|  | Spring Data JPA | H2 DB 연동 및 ORM |
|  | JJWT (io.jsonwebtoken) | JWT 토큰 생성 및 검증 |
|  | Lombok | Boilerplate 코드 제거 |
| **Database** | H2 (in-memory) | 개발/테스트용 메모리 DB |
| **Build & Tool** | Gradle (Groovy DSL) | 백엔드 빌드 |
|  | npm / CRA | 프론트엔드 빌드 |
| **Infra** | Localhost (3000 / 8080) | CRA proxy로 API 연동 |

<br />

## ⚙️ 주요 기능

### 🧾 회원가입 (`POST /api/auth/register`)
- 이메일 중복 확인 (`GET /api/auth/exists?email=...`)
- 이메일 형식 검증 (`@Email`)
- 비밀번호 규칙 검증 (8자 이상, 대소문자/숫자 포함)
- BCryptPasswordEncoder로 암호화 저장

### 🔐 로그인 (`POST /api/auth/login`)
- 이메일 및 비밀번호 검증
- 로그인 성공 시 JWT AccessToken / RefreshToken 발급
- RefreshToken은 DB(User 테이블)에 저장

### 🪪 내정보 조회 (`GET /api/user/me`)
- Authorization 헤더의 Bearer Token 검증
- `JwtAuthenticationFilter`가 인증 정보를 설정
- SecurityContext에 저장된 사용자 정보 반환

### 🌐 소셜 로그인 (OAuth2) (진행중)
- Google / Kakao 로그인 지원
- 최초 로그인 시 자동 회원가입
- 로그인 성공 후 JWT 발급 및 프론트 리다이렉트

[스프링부트 구글 소셜 로그인 구현하기](https://velog.io/@bdd14club/%EB%B0%B1%EC%97%94%EB%93%9C-2.-%EA%B5%AC%EA%B8%80-%EC%86%8C%EC%85%9C-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0) <br/>
[백엔드에서 소셜 로그인을 구현해보자(총정리)](https://velog.io/@hsh111366/Spring-Security-%EB%B0%B1%EC%97%94%EB%93%9C%EC%97%90%EC%84%9C-%EC%86%8C%EC%85%9C-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0-%ED%94%84%EB%A1%A0%ED%8A%B8%EC%97%90%EC%84%9C-%ED%95%B4%EC%95%BC-%ED%95%A0-%EC%9D%BC-%EC%B4%9D%EC%A0%95%EB%A6%AC-feat.-OAuth2.0) <br/>
[백엔드 소셜 로그인을 구현해보자](https://velog.io/@juuuunny/%EB%B0%B1%EC%97%94%EB%93%9C%EC%97%90%EC%84%9C-%EC%86%8C%EC%85%9C-%EB%A1%9C%EA%B7%B8%EC%9D%B8%EC%9D%84-%EA%B5%AC%ED%98%84%ED%95%B4%EB%B3%B4%EC%9E%90-%EC%B9%B4%EC%B9%B4%EC%98%A4-%EA%B5%AC%EA%B8%80-%EB%84%A4%EC%9D%B4%EB%B2%84-3%EC%A2%85-%EC%A0%81%EC%9A%A9%EA%B8%B0#35-%EC%9D%B8%EC%A6%9D-%EC%84%B1%EA%B3%B5-%EC%8B%9C)

<br />

## 🚀 개발 시나리오 예시

1. 회원가입 페이지에서 이메일과 비밀번호 입력
   - 이메일 중복체크 API 호출 (/api/auth/exists)
   - 유효성 검증 실패 시 메시지 표시
2. 비밀번호 검증 (영문/숫자 조합, 8자 이상)
3. 회원가입 성공 → 로그인 페이지 이동

   3-1. 소셜로그인으로 회원가입 시 레지스터 토큰 발급, 추가 정보 입력 후 회원가입 진행
4. 로그인 성공 → JWT 발급 후 localStorage 저장
5. 내정보 페이지(/api/user/me) 호출 시 Authorization 헤더 포함

<br />

## ⚠️ 주의 및 확장 포인트

- 실제 운영 환경에서는 H2 → MySQL/PostgreSQL 변경
- JWT Secret 키는 환경변수로 관리 (노출 금지)
- Refresh Token 저장 시 Redis 또는 별도 테이블 권장
- CSRF, CORS 정책 세분화 필요
- 소셜 로그인 시 이메일 미제공 케이스 처리
- [ 추가가 필요한 로직 ]
   1. 소셜 로그인을 통한 신규회원가입 시 추가정보를 입력하기 위한 레지스터 토큰 발급 로직 추가
   2. 소셜 로그인을 통한 기존회원 로그인에 대한 처리
   3. `Refresh Token`을 통한 `Access Token` 재발급 로직
   4. `Access Token`을 통한 `Refresh Token` 재발급 로직
   5. 로그아웃 기능 구현


<br />
<br />

## 👨‍💻 Author
Developed by: [YoonHan0](https://github.com/YoonHan0) <br />
Environment: macOS / Java 17 / Node.js 18 / Spring Boot 3.2
> 💡 본 프로젝트는 인증 로직의 기본 학습용 예제입니다. <br />
실서비스에서는 키 관리, 토큰 만료 정책, HTTPS 환경 설정 등이 추가되어야 합니다.

<br />
<br />

## 💡 추가로 알게된 개념
[URI, URL의 차이](https://inpa.tistory.com/entry/WEB-%F0%9F%8C%90-URL-URI-%EC%B0%A8%EC%9D%B4) <br/>
[Spring Security Context](https://wildeveloperetrain.tistory.com/324#google_vignette) <br/>
[쿠기 vs 세션 vs 토큰](https://inpa.tistory.com/entry/WEB-%F0%9F%93%9A-JWTjson-web-token-%EB%9E%80-%F0%9F%92%AF-%EC%A0%95%EB%A6%AC) <br/>
[Access Token, Refresh Token](https://inpa.tistory.com/entry/WEB-%F0%9F%93%9A-Access-Token-Refresh-Token-%EC%9B%90%EB%A6%AC-feat-JWT)