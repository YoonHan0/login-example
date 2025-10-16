package com.example.auth.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserLoginRequest {
    private String email;
    private String password;
}
