package com.example.auth.controller;

import com.example.auth.dto.UserJoinRequest;
import com.example.auth.dto.UserLoginRequest;
import com.example.auth.dto.TokenResponse;
import com.example.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor    // 자동적으로 final 필드 생성자를 만들어줌
public class AuthController {

    private final AuthService authService;

    @GetMapping("/exists")
    public ResponseEntity<Boolean> exists(@RequestParam("email") String email) {
        return ResponseEntity.ok(authService.existsByEmail(email));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserJoinRequest req) {
        authService.register(req);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody UserLoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }
}
