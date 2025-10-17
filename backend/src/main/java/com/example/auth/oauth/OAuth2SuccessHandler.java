package com.example.auth.oauth;

import com.example.auth.dto.GoogleUserInfo;
import com.example.auth.dto.OAuth2UserInfo;
import com.example.auth.entity.User;
import com.example.auth.jwt.JwtTokenProvider;
import com.example.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

//    @Value("${spring.jwt.redirect.onboarding}")
//    private String REDIRECT_URI_ONBOARDING; // 신규 유저 리다이렉트할 URI
//    @Value("${spring.jwt.redirect.base}")
//    private String REDIRECT_URI_BASE; // 기존 유저 리다이렉트할 URI

    private OAuth2UserInfo oAuth2UserInfo = null;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;

        final String provider = token.getAuthorizedClientRegistrationId();
        final Map<String, Object> attributes = token.getPrincipal().getAttributes();

        switch (provider) {
//            case "kakao" -> oAuth2UserInfo = new KakaoUserInfo(attributes);
            case "google" -> oAuth2UserInfo = new GoogleUserInfo(attributes);
//            case "naver" -> oAuth2UserInfo = new NaverUserInfo(attributes);
        }

        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();

        User existUser = userRepository.findByProviderId(providerId);

        if (existUser == null) {
            log.info("✨ 신규 유저입니다.");
            log.info(oAuth2UserInfo.toString());

            String redirectUrl = UriComponentsBuilder
                    .fromUriString("http://localhost:3000/signup")
                    .queryParam("email", oAuth2UserInfo.getEmail())
                    .queryParam("name", oAuth2UserInfo.getName())
                    .build()
                    .encode()
                    .toUriString();

            response.sendRedirect(redirectUrl);

            return;

        } else {
            log.info("🧑‍💻 기존 유저입니다.");
            log.info(oAuth2UserInfo.toString());
        }
    }


}
