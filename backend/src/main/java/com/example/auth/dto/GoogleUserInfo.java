package com.example.auth.dto;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class GoogleUserInfo implements OAuth2UserInfo {

    private final Map<String, Object> attribute;

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProviderId() {
        return attribute.get("sub").toString();
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString();
    }

    @Override
    public String getName() {
        return attribute.get("name").toString();
    }

    @Override
    public String toString() {
        String provider = "google";
        String providerId = attribute.get("sub").toString();
        String email = attribute.get("email").toString();
        String name = attribute.get("name").toString();

        return "\n [Google Info] \n" + "provider: " + provider + ", providerId: " + providerId + ", email: " + email + ", name: " + name;
    }
}
