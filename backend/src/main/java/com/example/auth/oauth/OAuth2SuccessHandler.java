package com.example.auth.oauth;

import com.example.auth.dto.GoogleUserInfo;
import com.example.auth.dto.OAuth2UserInfo;
import com.example.auth.entity.User;
import com.example.auth.jwt.JwtTokenProvider;
import com.example.auth.repository.UserRepository;
import com.example.auth.security.JWTUtil;
import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
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
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

//    @Value("${spring.jwt.redirect.onboarding}")
//    private String REDIRECT_URI_ONBOARDING; // 신규 유저 리다이렉트할 URI
//    @Value("${spring.jwt.redirect.base}")
//    private String REDIRECT_URI_BASE; // 기존 유저 리다이렉트할 URI


    @Value("${app.redirect.onboarding}")
    private String REDIRECT_URI_ONBOARDING;

    @Value("${jwt.register-expire-ms}")
    private long REGISTER_TOKEN_EXPIRATION_TIME;

    private OAuth2UserInfo oAuth2UserInfo = null;
    private final JwtTokenProvider jwtTokenProvider;
//    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;

        String provider = token.getAuthorizedClientRegistrationId();
        Map<String, Object> attributes = token.getPrincipal().getAttributes();

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

            /*
            // 신규 유저일 경우 - 레지스터 토큰 발급
            String registerToken = jwtUtil.generateRegisterToken(provider, providerId, email, REGISTER_TOKEN_EXPIRATION_TIME);

            // HttpOnly, Secure 쿠기로 설정 (JS에서 읽지 못하게)
            Cookie cookie = new Cookie("registerToken", registerToken);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            cookie.setMaxAge((int)(REGISTER_TOKEN_EXPIRATION_TIME / 1000));
            response.addCookie(cookie);

            // 온보딩 페이지로 리다이렉트 (쿼리파라미터로 민감정보 전달 X)
            getRedirectStrategy().sendRedirect(request, response, REDIRECT_URI_ONBOARDING);
            return;
            */

            // [임시] 레지스터 토큰 구현 전까지 해당 로직을 실행
            String redirectUrl = UriComponentsBuilder
                    .fromUriString(REDIRECT_URI_ONBOARDING)
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
