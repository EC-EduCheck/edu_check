package org.example.educheck.global.common.exception.custom.auth;

import org.example.educheck.global.common.exception.ErrorCode;
import org.example.educheck.global.common.exception.custom.common.GlobalException;

public class EmailNotFoundException extends GlobalException {
    public EmailNotFoundException() {
        super(ErrorCode.EMAIL_NOT_FOUND);
    }

    public EmailNotFoundException(String customMessage) {
        super(ErrorCode.EMAIL_NOT_FOUND, customMessage);
    }
}
