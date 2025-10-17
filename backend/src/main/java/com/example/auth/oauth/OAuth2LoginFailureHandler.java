package com.example.auth.oauth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        // 로그: 실패 이유를 로깅
        log.warn("OAuth2 login failed: {}", exception.getMessage(), exception);

        // 클라이언트에 JSON 에러 응답을 주고 싶으면 아래처럼 작성 가능
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        String body = "{\"error\":\"OAuth2 로그인에 실패했습니다.\", \"message\":\"" + exception.getMessage() + "\"}";
        response.getWriter().write(body);

        // 만약 리다이렉트가 필요하면 아래 주석을 해제하고 사용
        // getRedirectStrategy().sendRedirect(request, response, "/login?error");
    }
}
