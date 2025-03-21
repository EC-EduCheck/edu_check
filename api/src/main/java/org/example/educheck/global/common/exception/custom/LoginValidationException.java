package org.example.educheck.global.common.exception.custom;

import org.example.educheck.global.common.exception.ErrorCode;
import org.example.educheck.global.common.exception.custom.common.GlobalException;

public class LoginValidationException extends GlobalException {
    public LoginValidationException() {
        super(ErrorCode.UNAUTHORIZED, ErrorCode.UNAUTHORIZED.getMessage());
    }
}
