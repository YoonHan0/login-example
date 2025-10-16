package com.example.auth.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserDto {
    private String email;
    private String nickname;
    private String provider;
}
