package org.example.educheck.global.common.exception.custom.common;

import org.example.educheck.global.common.exception.ErrorCode;

public class InvalidRequestException extends GlobalException {
    public InvalidRequestException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidRequestException() {
        super(ErrorCode.INVALID_INPUT, ErrorCode.INVALID_INPUT.getMessage());
    }

    public InvalidRequestException(String message) {
        super(ErrorCode.INVALID_INPUT, message);
    }
}
