package org.example.educheck.global.security.config;

public class SecurityPathConfig {

    public static final String[] PUBLIC_POST_URLS = {
            "/api/auth/login",
            "/api/auth/signup",
            "/api/auth/refresh",
            "/api/auth/email-check",
    };

    public static final String[] PUBLIC_GET_URLS = {
            "/error",
    };
}
