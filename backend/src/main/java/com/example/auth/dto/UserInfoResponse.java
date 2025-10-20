package com.example.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoResponse {

    private String nickname;

    public UserInfoResponse(String nickname) {
        this.nickname = nickname;
    }
}
