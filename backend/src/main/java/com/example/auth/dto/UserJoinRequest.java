package com.example.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserJoinRequest {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String nickname;
}
