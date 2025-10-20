package com.example.auth.service;

import com.example.auth.dto.UserJoinRequest;
import com.example.auth.dto.UserLoginRequest;
import com.example.auth.dto.TokenResponse;
import com.example.auth.entity.User;
import com.example.auth.jwt.JwtTokenProvider;
import com.example.auth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    /* JWT payload에 담긴 userId 기반으로 DB 조회 */

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    public void register(UserJoinRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        if (!isValidPassword(req.getPassword())) {
            throw new IllegalArgumentException("비밀번호 규칙에 맞지 않습니다.");
        }
        User u = User.builder()
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .nickname(req.getNickname())
                .provider("LOCAL")
                .providerId(null)
                .build();
        userRepository.save(u);
    }

    public TokenResponse login(UserLoginRequest req) {

        User u = userRepository.findByEmail(req.getEmail()).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (u.getProvider() != null && !"LOCAL".equals(u.getProvider()) && u.getPassword() == null) {
            throw new IllegalArgumentException("소셜 계정으로 가입된 사용자입니다.");
        }
        if (!passwordEncoder.matches(req.getPassword(), u.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String access = jwtTokenProvider.createAccessToken(u.getEmail());
        String refresh = jwtTokenProvider.createRefreshToken(u.getEmail());

        u.setRefreshToken(refresh);

        userRepository.save(u);     // Refresh Token 저장을 위한 save ?

        return new TokenResponse(access, refresh);
    }

    private boolean isValidPassword(String pw) {
        return pw != null && pw.matches("^(?=.*[0-9])(?=.*[!@#$%^&*])[A-Za-z0-9!@#$%^&*]{8,20}$");
    }
}
