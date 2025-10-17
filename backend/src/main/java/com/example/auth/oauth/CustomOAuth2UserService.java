package com.example.auth.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {

        System.out.println("===== CustomOAuth2UserService 호출 =====");
        OAuth2User user = super.loadUser(userRequest);

        // 여기서 속성 매핑/정제 작업을 추가할 수 있음
        return user;
    }
}
