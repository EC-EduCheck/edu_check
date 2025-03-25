package org.example.educheck.global.common.exception.custom.common;


import org.example.educheck.global.common.exception.ErrorCode;

public class NotOwnerException extends GlobalException {
    public NotOwnerException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotOwnerException() {
        super(ErrorCode.NOT_OWNER, ErrorCode.NOT_OWNER.getMessage());
    }

    public NotOwnerException(String message) {
        super(ErrorCode.NOT_OWNER, message);
    }
}
