package org.example.educheck.global.common.exception.custom.common;

import org.example.educheck.global.common.exception.ErrorCode;

public class ForbiddenException extends GlobalException {

    public ForbiddenException(String customMessage) {
        super(ErrorCode.FORBIDDEN, customMessage);
    }

    public ForbiddenException() {
        super(ErrorCode.FORBIDDEN, ErrorCode.FORBIDDEN.getMessage());
    }
}
