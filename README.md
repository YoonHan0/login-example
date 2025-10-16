# auth-example-complete2

This project contains a Spring Boot (Gradle) backend with JWT + OAuth2 integration (Kakao/Google placeholders)
and a simple Create-React-App frontend.

## Backend (run)
cd backend
./gradlew bootRun

## Frontend (run)
cd frontend
npm install
npm start

## Notes
- application.yml contains dummy OAuth client ids/secrets under spring.security.oauth2.client.registration.*
- JWT secret is in application.yml under jwt.secret
- For real OAuth logins replace client-id and client-secret with values from providers.
