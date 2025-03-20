package org.example.educheck.global.common.exception.custom.common;

import lombok.Getter;
import org.example.educheck.global.common.exception.ErrorCode;

@Getter
public class GlobalException extends RuntimeException {

    private ErrorCode errorCode;

    public GlobalException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public GlobalException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
