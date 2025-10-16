package com.example.auth.oauth;

import com.example.auth.entity.User;
import com.example.auth.jwt.JwtTokenProvider;
import com.example.auth.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public OAuth2SuccessHandler(JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oauthUser.getAttributes();

        String email = null;
        String name = null;
        String provider = "social";

        // Kakao returns kakao_account
        if (attributes.containsKey("kakao_account")) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            email = (String) kakaoAccount.get("email");
            Object profile = kakaoAccount.get("profile");
            if (profile instanceof Map) {
                name = (String) ((Map) profile).get("nickname");
            }
            provider = "kakao";
        } else if (attributes.containsKey("email")) { // Google
            email = (String) attributes.get("email");
            name = (String) attributes.getOrDefault("name", null);
            provider = "google";
        } else {
            // fallback (non-email provider)
            email = String.valueOf(attributes.get("id"));
        }

        if (email == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "OAuth provider did not return email");
            return;
        }

        Optional<User> existing = userRepository.findByEmail(email);
        User user;
        if (existing.isPresent()) {
            user = existing.get();
            if (user.getProvider() == null) user.setProvider(provider);
            if (user.getNickname() == null && name != null) user.setNickname(name);
        } else {
            user = new User();
            user.setEmail(email);
            user.setNickname(name == null ? email.split("@")[0] : name);
            user.setProvider(provider);
            userRepository.save(user);
        }

        // create tokens and persist refresh
        String access = jwtTokenProvider.createAccessToken(email);
        String refresh = jwtTokenProvider.createRefreshToken(email);
        user.setRefreshToken(refresh);
        userRepository.save(user);

        // redirect to front with tokens (demo - in prod use HttpOnly cookies)
        String redirect = String.format("%s?accessToken=%s&refreshToken=%s", "http://localhost:3000", access, refresh);
        response.sendRedirect(redirect);
    }
}
