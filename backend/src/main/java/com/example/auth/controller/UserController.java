package com.example.auth.controller;

import com.example.auth.entity.User;
import com.example.auth.repository.UserRepository;
import com.example.auth.jwt.JwtTokenProvider;
import com.example.auth.security.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public UserController(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal CustomUserDetails userDetail) {

        if (userDetail == null) {
            System.out.println("[Error] 인증 정보가 없습니다.");
            return ResponseEntity.status(401).body(Map.of("error", "인증 정보가 없습니다."));
        }
        System.out.println("=== me 호출 ===");
        System.out.println(userDetail.toString());

        String email = userDetail.getUser().getEmail();
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(user);
    }
}
