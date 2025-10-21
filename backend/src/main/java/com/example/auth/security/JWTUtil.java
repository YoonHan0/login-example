package com.example.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

//@Slf4j
//@Component
//@RequiredArgsConstructor
public class JWTUtil {

//    private final SecretKey secretKey;      // 생성자에서 secret 초기화
//
////    public JWTUtil(@Value("${jwt.secret}") String secret) {
////        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
////    }
//
//    public String generateRegisterToken(String provider, String providerId, String email, long expirationMillis) {
//        return Jwts.builder()
//                .claim("provider", provider)
//                .claim("providerId", providerId)
//                .claim("email", email)
//                .setIssuedAt(new Date())    // 발급시간
//                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis)) // 만료시간
//                .signWith(secretKey, SignatureAlgorithm.HS256)      // 서명
//                .compact();
//    }
//
//    public Claims parseClaims(String token) {
//
//        try {
//            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
//
//        } catch (ExpiredJwtException e) {
//            throw new RuntimeException("REGISTER_TOKEN_EXPIRED");
//        } catch (Exception e) {
//            throw new RuntimeException("INVALID_REGISTER_TOKEN");
//        }
//    }
//    // Register Token 에서 provider 추출
//    public String getProviderFromRegisterToken(String token) {
//        return tokenParser(token).get("provider", String.class);
//    }
//
//    // Register Token 에서 providerId 추출
//    public String getProviderIdFromRegisterToken(String token) {
//        return tokenParser(token).get("providerId", String.class);
//    }
//
//    // Register Token 에서 email 추출
//    public String getEmailFromRegisterToken(String token) {
//        return tokenParser(token).get("email", String.class);
//    }
//
//    private Claims tokenParser(String token) {
//        try {
//            return Jwts.parserBuilder()
//                    .setSigningKey(secretKey)
//                    .build()
//                    .parseClaimsJws(token)
//                    .getBody();
//        } catch (ExpiredJwtException e) {
////            throw new CustomException(AuthErrorStatus._EXPIRED_REGISTER_TOKEN);  // 토큰 만료 처리
//            System.out.println("토큰 만료 처리해야함");
//            throw e;
//        } catch (Exception e) {
////            throw new CustomException(AuthErrorStatus._INVALID_TOKEN);  // 유효하지 않은 토큰 처리
//            System.out.println("유효하지 않은 토큰 처리해야함");
//            throw e;
//        }
//    }
//
//    public String getProvider(String token) { return parseClaims(token).get("provider", String.class); }
//    public String getProviderId(String token) { return parseClaims(token).get("providerId", String.class); }
//    public String getEmail(String token) { return parseClaims(token).get("email", String.class); }
}
