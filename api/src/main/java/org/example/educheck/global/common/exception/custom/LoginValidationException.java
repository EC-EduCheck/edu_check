package org.example.educheck.global.common.exception.custom;

import org.springframework.security.core.AuthenticationException;

public class LoginValidationException extends AuthenticationException {
    public LoginValidationException() {
        super("로그인에 실패했습니다.");
    }
}
