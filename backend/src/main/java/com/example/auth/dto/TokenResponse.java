package com.example.auth.dto;

import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
}
