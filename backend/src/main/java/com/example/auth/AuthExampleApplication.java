package com.example.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AuthExampleApplication {

    @Value("${GOOGLE_CLIENT_ID}")
    private String googleClientId;

    @Value("${GOOGLE_CLIENT_SECRET}")
    private String googleClientSecret;

    public static void main(String[] args) {
        SpringApplication.run(AuthExampleApplication.class, args);
    }

    @Bean
    public CommandLineRunner testEnv() {
        return args -> {
            System.out.println("[소셜 로그인 권한 계정 확인(Google)]");
            System.out.println("GOOGLE_CLIENT_ID = " + googleClientId);
            System.out.println("GOOGLE_CLIENT_SECRET = " + googleClientSecret);
        };
    }
}
