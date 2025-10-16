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
        Map<String, Object> attr = oauthUser.getAttributes();

        // Extract email depending on provider structure
        String email = null;
        if (attr.containsKey("kakao_account")) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attr.get("kakao_account");
            email = (String) kakaoAccount.get("email");
        } else if (attr.containsKey("email")) {
            email = (String) attr.get("email");
        }

        if (email == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "OAuth provider did not return email");
            return;
        }

        String provider = oauthUser.getName(); // may not be provider name; we will attempt to detect
        String providerId = authentication.getAuthorities().toString();

        Optional<User> maybe = userRepository.findByEmail(email);
        User user;
        if (maybe.isPresent()) {
            user = maybe.get();
        } else {
            user = User.builder()
                    .email(email)
                    .provider("SOCIAL")
                    .nickname(email.split("@")[0])
                    .build();
            userRepository.save(user);
        }

        String access = jwtTokenProvider.createAccessToken(email);
        String refresh = jwtTokenProvider.createRefreshToken(email);
        user.setRefreshToken(refresh);
        userRepository.save(user);

        // Redirect to frontend with token in query (for demo only)
        String redirect = "http://localhost:3000/?accessToken=" + access + "&refreshToken=" + refresh;
        response.sendRedirect(redirect);
    }
}
