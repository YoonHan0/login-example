package com.example.auth.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    // 암호화된 비밀번호 (로컬 가입 시에만 존재)
    private String password;

    // provider: LOCAL, GOOGLE, KAKAO
    private String provider;

    @Column(nullable = true, unique = true)
    private String providerId;

    private String nickname;

    // refresh token (optional)
    @Column(length = 500)
    private String refreshToken;
}
